import { createContext } from "react";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { GameRepository } from "../../domain/Game";
import { Alert } from "~/modules/shared/application/Alert";

export const GameFormContext = createContext<Context | undefined>(undefined)

export function useGameFormContext() {
    return useContextHandler(GameFormContext)
}

type Context = {
    repository: GameRepository,
    alert: Alert
}