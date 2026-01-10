import { useBusSubscriber } from "~/modules/shared/infra/hooks/useBusSubscriber";
import { useGameViewerContext } from "./GameViewerContext";
import { GameEvents } from "../../GameEvents";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { useState } from "react";

export function useGameViewerPresenter() {
  const { busSubscriber, routeNavigate } = useGameViewerContext();
  const [game, setGame] = useState<GameQueryRepository.Output | null>(null);

  const onClickEdit = () => {
    if (!game) return;
    routeNavigate.navigateTo(`/game/${game?.id}/edit`);
  };

  useBusSubscriber({
    bus: busSubscriber,
    eventName: GameEvents.GAME_SELECTED,
    handler: setGame,
  });

  return {
    game,
    onClickEdit,
  };
}
