import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";
import { GameListContext } from "./features/GameList/GameListContext";
import { LocalPlatformGateway } from "../platforms/gateways/LocalPlatformGateway";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";

export function GameProviders({ children }: PropsWithChildren) {
    const repository = useMemo(() => new LocalGameGateway(), []);
    const platformSearchService = useMemo(() => new LocalPlatformGateway(), []);

    return (
        <GameListContext.Provider value={{ alert: toast, queryRepository: repository }}>
            <GameFormContext.Provider value={{
                repository,
                alert: toast,
                platformSearchService,
                routeNavigator: useReactRouterRouteNavigator()
            }}>
                {children}
            </GameFormContext.Provider>
        </GameListContext.Provider>
    )
}