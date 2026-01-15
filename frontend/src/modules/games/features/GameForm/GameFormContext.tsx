import { createContext } from "react";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { GameRepository } from "../../domain/Game";
import { Alert } from "~/modules/shared/application/Alert";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";
import { PlatformTypesService } from "../../services/PlatformTypesService";

export const GameFormContext = createContext<Context | undefined>(undefined)

export function useGameFormContext() {
    return useContextHandler(GameFormContext)
}

type Context = {
    platformTypesService: PlatformTypesService,
    repository: GameRepository,
    alert: Alert
    routeNavigator: RouteNavigator
}