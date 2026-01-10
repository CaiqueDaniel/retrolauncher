import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";
import { GameListContext } from "./features/GameList/GameListContext";
import { LocalPlatformGateway } from "../platforms/gateways/LocalPlatformGateway";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";
import { EventBus } from "../shared/infra/services/EventBus";
import { GameViewerContext } from "./features/GameViewer/GameViewerContext";
import { FilepathSelectorContext } from "../shared/infra/features/FilepathSelector/FilepathSelectorContext";
import { LocalFilepathSelectionService } from "../shared/infra/services/LocalFilepathSelectionService";

export function GameProviders({ children }: PropsWithChildren) {
    const repository = useMemo(() => new LocalGameGateway(), []);
    const platformSearchService = useMemo(() => new LocalPlatformGateway(), []);
    const filepathSelectionService = useMemo(() => new LocalFilepathSelectionService(), []);

    return (
        <GameListContext.Provider value={{
            alert: toast,
            queryRepository: repository,
            busDispatcher: EventBus.getInstance()
        }}>
            <GameViewerContext.Provider value={{
                busSubscriber: EventBus.getInstance(),
                routeNavigate: useReactRouterRouteNavigator()
            }}>
                <GameFormContext.Provider value={{
                    repository,
                    alert: toast,
                    platformSearchService,
                    routeNavigator: useReactRouterRouteNavigator()
                }}>
                    <FilepathSelectorContext.Provider value={{
                        filepathSelectionService
                    }}>
                        {children}
                    </FilepathSelectorContext.Provider>
                </GameFormContext.Provider>
            </GameViewerContext.Provider>
        </GameListContext.Provider>
    )
}