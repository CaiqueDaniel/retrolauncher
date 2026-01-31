import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";
import { GameListContext } from "./features/GameList/GameListContext";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";
import { EventBus } from "../shared/infra/services/EventBus";
import { GameViewerContext } from "./features/GameViewer/GameViewerContext";

export function GameProviders({ children }: PropsWithChildren) {
  const gameGateway = useMemo(() => new LocalGameGateway(), []);

  return (
    <GameListContext.Provider
      value={{
        alert: toast,
        queryRepository: gameGateway,
        busDispatcher: EventBus.getInstance(),
      }}
    >
      <GameViewerContext.Provider
        value={{
          busSubscriber: EventBus.getInstance(),
          routeNavigate: useReactRouterRouteNavigator(),
          startGameService: gameGateway,
        }}
      >
        <GameFormContext.Provider
          value={{
            repository: gameGateway,
            alert: toast,
            platformTypesService: gameGateway,
            routeNavigator: useReactRouterRouteNavigator(),
          }}
        >
          {children}
        </GameFormContext.Provider>
      </GameViewerContext.Provider>
    </GameListContext.Provider>
  );
}
