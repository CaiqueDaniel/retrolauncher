import { createContext } from "react";
import { BusSubscriber } from "~/modules/shared/application/BusSubscriber";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";

export const GameViewerContext = createContext<Context | undefined>(undefined);

export function useGameViewerContext() {
    return useContextHandler(GameViewerContext);
}

type Context = {
    busSubscriber: BusSubscriber;
    routeNavigate: RouteNavigator
}