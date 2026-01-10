import { renderHook, waitFor, act } from "@testing-library/react";
import { useGameViewerPresenter } from "./useGameViewerPresenter";
import { GameViewerContext } from "./GameViewerContext";
import {
  describe,
  it,
  expect,
  vi,
  beforeEach,
  afterEach,
  Mocked,
} from "vitest";
import { ReactNode } from "react";
import { GameEvents } from "../../GameEvents";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { EventBus } from "~/modules/shared/infra/services/EventBus";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";

describe("useGameViewerPresenter", () => {
  const eventBus = EventBus.getInstance();
  let routeNavigate: Mocked<RouteNavigator>;

  beforeEach(() => {
    eventBus.clear();
    routeNavigate = { navigateTo: vi.fn() };
  });

  afterEach(() => {
    vi.clearAllMocks();
    eventBus.clear();
  });

  it("should initialize with null game", () => {
    const { result } = renderHook(() => useGameViewerPresenter(), { wrapper });

    expect(result.current.game).toBeNull();
  });

  it("should update game when GAME_SELECTED event is dispatched", async () => {
    const mockGame: GameQueryRepository.Output = {
      id: "1",
      name: "Super Mario 64",
      platform: "Nintendo 64",
      cover: "/path/to/cover.jpg",
    };

    const { result } = renderHook(() => useGameViewerPresenter(), { wrapper });

    // Dispatch event
    await act(async () => {
      eventBus.dispatch(GameEvents.GAME_SELECTED, mockGame);
    });

    await waitFor(() => {
      expect(result.current.game).toEqual(mockGame);
    });
  });

  it("should update game when different games are selected", async () => {
    const firstGame: GameQueryRepository.Output = {
      id: "1",
      name: "Super Mario 64",
      platform: "Nintendo 64",
      cover: "/path/to/cover1.jpg",
    };

    const secondGame: GameQueryRepository.Output = {
      id: "2",
      name: "The Legend of Zelda",
      platform: "Nintendo 64",
      cover: "/path/to/cover2.jpg",
    };

    const { result } = renderHook(() => useGameViewerPresenter(), { wrapper });

    // First game selection
    await act(async () => {
      eventBus.dispatch(GameEvents.GAME_SELECTED, firstGame);
    });

    await waitFor(() => {
      expect(result.current.game).toEqual(firstGame);
    });

    // Second game selection
    await act(async () => {
      eventBus.dispatch(GameEvents.GAME_SELECTED, secondGame);
    });

    await waitFor(() => {
      expect(result.current.game).toEqual(secondGame);
    });
  });

  it("should unsubscribe on unmount", () => {
    const unsubscribeSpy = vi.spyOn(eventBus, "unsubscribe");

    const { unmount } = renderHook(() => useGameViewerPresenter(), { wrapper });

    unmount();

    expect(unsubscribeSpy).toHaveBeenCalled();
  });

  it("should not update game after unmounting", async () => {
    const mockGame: GameQueryRepository.Output = {
      id: "1",
      name: "Super Mario 64",
      platform: "Nintendo 64",
      cover: "/path/to/cover.jpg",
    };

    const { result, unmount } = renderHook(() => useGameViewerPresenter(), {
      wrapper,
    });

    expect(result.current.game).toBeNull();

    unmount();

    // Try to dispatch event after unmount
    act(() => {
      eventBus.dispatch(GameEvents.GAME_SELECTED, mockGame);
    });

    // Game should still be null since component was unmounted
    expect(result.current.game).toBeNull();
  });

  it("should navigate to edit page when onClickEdit and game is set", async () => {
    const mockGame: GameQueryRepository.Output = {
      id: "1",
      name: "Super Mario 64",
      platform: "Nintendo 64",
      cover: "/path/to/cover.jpg",
    };

    const { result } = renderHook(() => useGameViewerPresenter(), {
      wrapper,
    });

    await act(async () => {
      eventBus.dispatch(GameEvents.GAME_SELECTED, mockGame);
    });

    await waitFor(() => {
      expect(result.current.game).toEqual(mockGame);
    });

    act(() => {
      result.current.onClickEdit();
    });

    expect(routeNavigate.navigateTo).toHaveBeenCalledWith(
      `/game/${mockGame.id}/edit`
    );
  });

  it("should not navigate when onClickEdit and game is null", () => {
    const { result } = renderHook(() => useGameViewerPresenter(), {
      wrapper,
    });

    expect(result.current.game).toBeNull();

    act(() => {
      result.current.onClickEdit();
    });

    expect(routeNavigate.navigateTo).not.toHaveBeenCalled();
  });

  function wrapper({ children }: { children: ReactNode }) {
    return (
      <GameViewerContext.Provider
        value={{
          busSubscriber: eventBus,
          routeNavigate: routeNavigate,
        }}
      >
        {children}
      </GameViewerContext.Provider>
    );
  }
});
