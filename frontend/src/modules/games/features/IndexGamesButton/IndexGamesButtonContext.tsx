import { createContext } from "react";
import { IndexGamesService } from "../../services/IndexGamesService";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { Alert } from "~/modules/shared/application/Alert";
import { BusDispatcher } from "~/modules/shared/application/BusDispatcher";

export const IndexGamesButtonContext = createContext<Context | undefined>(undefined);

export function useIndexGamesButtonContext() {
    return useContextHandler(IndexGamesButtonContext);
}

type Context = {
    indexGamesService: IndexGamesService,
    alert: Alert
    busDispatcher: BusDispatcher
}