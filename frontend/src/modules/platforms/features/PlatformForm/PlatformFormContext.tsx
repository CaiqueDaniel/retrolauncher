import { createContext } from "react";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { Alert } from "~/modules/shared/application/Alert";
import { PlatformRepository } from "../../domain/Platform";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";

export const PlatformFormContext = createContext<Context | undefined>(undefined)

export function usePlatformFormContext() {
    return useContextHandler(PlatformFormContext)
}

type Context = {
    repository: PlatformRepository,
    alert: Alert
    routeNavigator: RouteNavigator
}