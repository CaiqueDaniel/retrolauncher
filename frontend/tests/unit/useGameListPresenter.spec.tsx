import { renderHook, waitFor } from "@testing-library/react";
import { PropsWithChildren } from "react";
import { Mocked } from "vitest";
import { GameListContext } from "~/modules/games/features/GameList/GameListContext";
import { useGameListPresenter } from "~/modules/games/features/GameList/useGameListPresenter";
import { GameEvents } from "~/modules/games/GameEvents";
import { GameQueryRepository } from "~/modules/games/repositories/GameQueryRepository";
import { Alert } from "~/modules/shared/application/Alert";
import { EventBus } from "~/modules/shared/infra/services/EventBus";

describe("useGameListPresenter", () => {
  const options = { wrapper: Provider };
  let queryRepository: Mocked<GameQueryRepository.Repository>;
  let alert: Mocked<Alert>;

  beforeAll(() => {
    queryRepository = {
      search: vi.fn(),
    };
    alert = {
      error: vi.fn(),
      success: vi.fn(),
    };
  });

  afterEach(() => {
    vi.clearAllMocks();
  });

  it("should be able to list games", async () => {
    const id = crypto.randomUUID();

    queryRepository.search.mockResolvedValue([
      {
        id,
        name: "Game",
        platform: "NES",
        cover: "https://example.com/cover.jpg",
      },
    ]);

    const { result } = renderHook(() => useGameListPresenter(), options);

    expect(result.current.games).toEqual([]);

    await waitFor(() => {
      expect(result.current.games).toEqual([
        {
          id,
          name: "Game",
          platform: "NES",
          cover: "https://example.com/cover.jpg",
        },
      ]);
    });
  });

  it("should be able to handle error when fetching games", async () => {
    queryRepository.search.mockRejectedValue(new Error("Network error"));

    const { result } = renderHook(() => useGameListPresenter(), options);

    expect(result.current.games).toEqual([]);

    await waitFor(() => {
      expect(result.current.games).toEqual([]);
      expect(alert.error).toHaveBeenCalledWith(
        "Não foi possível carregar os jogos"
      );
    });
  });

  it('should be able to dispatch selected game', async () => {
    const id = crypto.randomUUID();
    const callback = vi.fn();

    queryRepository.search.mockResolvedValue([
      {
        id,
        name: "Game",
        platform: "NES",
        cover: "https://example.com/cover.jpg",
      },
    ]);

    EventBus.getInstance().subscribe(GameEvents.GAME_SELECTED, callback);

    const { result } = renderHook(() => useGameListPresenter(), options);

    await waitFor(() => {
      expect(result.current.games).toEqual([
        {
          id,
          name: "Game",
          platform: "NES",
          cover: "https://example.com/cover.jpg",
        },
      ]);
    });

    result.current.onClick(id);

    expect(callback).toHaveBeenCalledWith({
      id,
      name: "Game",
      platform: "NES",
      cover: "https://example.com/cover.jpg",
    });
  });

  function Provider({ children }: PropsWithChildren) {
    return (
      <GameListContext.Provider value={{ queryRepository, alert, busDispatcher: EventBus.getInstance() }}>
        {children}
      </GameListContext.Provider>
    );
  }
});
