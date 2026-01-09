import { createContext } from "react";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { GameRepository } from "../../domain/Game";
import { Alert } from "~/modules/shared/application/Alert";
import { PlatformSearchService } from "~/modules/platforms/services/PlatformSearchService";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";

export const GameFormContext = createContext<Context | undefined>(undefined)

export function useGameFormContext() {
    return useContextHandler(GameFormContext)
}

type Context = {
    platformSearchService: PlatformSearchService,
    repository: GameRepository,
    alert: Alert
    routeNavigator: RouteNavigator
}