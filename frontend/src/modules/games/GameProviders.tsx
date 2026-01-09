import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";
import { GameListContext } from "./features/GameList/GameListContext";
import { LocalPlatformGateway } from "../platforms/gateways/LocalPlatformGateway";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";
import { EventBus } from "../shared/infra/services/EventBus";
import { GameViewerContext } from "./features/GameViewer/GameViewerContext";

export function GameProviders({ children }: PropsWithChildren) {
    const repository = useMemo(() => new LocalGameGateway(), []);
    const platformSearchService = useMemo(() => new LocalPlatformGateway(), []);

    return (
        <GameListContext.Provider value={{
            alert: toast,
            queryRepository: repository,
            busDispatcher: EventBus.getInstance()
        }}>
            <GameViewerContext.Provider value={{
                busSubscriber: EventBus.getInstance()
            }}>
                <GameFormContext.Provider value={{
                    repository,
                    alert: toast,
                    platformSearchService,
                    routeNavigator: useReactRouterRouteNavigator()
                }}>
                    {children}
                </GameFormContext.Provider>
            </GameViewerContext.Provider>
        </GameListContext.Provider>
    )
}