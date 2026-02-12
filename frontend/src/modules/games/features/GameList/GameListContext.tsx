import { createContext } from "react";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { Alert } from "~/modules/shared/application/Alert";
import { BusDispatcher } from "~/modules/shared/application/BusDispatcher";
import { BusSubscriber } from "~/modules/shared/application/BusSubscriber";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";

export const GameListContext = createContext<Context | undefined>(undefined);

export function useGameListContext() {
  return useContextHandler(GameListContext);
}

type Context = {
  queryRepository: GameQueryRepository.Repository;
  alert: Alert;
  busDispatcher: BusDispatcher;
  busSubscriber: BusSubscriber;
};
