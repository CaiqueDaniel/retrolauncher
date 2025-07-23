import { createContext, useContext } from "react";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { Alert } from "~/modules/shared/application/Alert";

export const GameListContext = createContext<Context | undefined>(undefined);

export function useGameListContext() {
  const context = useContext(GameListContext);
  if (!context) {
    throw new Error(
      "useGameListContext must be used within a GameListProvider"
    );
  }
  return context;
}

type Context = {
  queryRepository: GameQueryRepository.Repository;
  alert: Alert;
};
