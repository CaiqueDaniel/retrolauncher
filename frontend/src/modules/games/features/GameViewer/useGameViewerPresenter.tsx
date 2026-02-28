import { useBusSubscriber } from "~/modules/shared/infra/hooks/useBusSubscriber";
import { useGameViewerContext } from "./GameViewerContext";
import { GameEvents } from "../../GameEvents";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { useState } from "react";

export function useGameViewerPresenter() {
  const { busSubscriber, routeNavigate, startGameService, gameShortcutService, alert } =
    useGameViewerContext();
  const [game, setGame] = useState<GameQueryRepository.Output | null>(null);

  const onClickEdit = () => {
    if (!game) return;
    routeNavigate.navigateTo(`/game/${game?.id}/edit`);
  };

  const onClickStart = () => {
    if (!game) return;
    startGameService.startGame(game.id);
  };

  const onClickCreateShortcut = () => {
    if (!game) return;
    gameShortcutService.createDesktopShortcut(game.id)
      .then(() => alert.success("Atalho criado com sucesso!"))
      .catch(alert.error);
  };

  useBusSubscriber({
    bus: busSubscriber,
    eventName: GameEvents.GAME_SELECTED,
    handler: setGame,
  });

  return {
    game,
    onClickEdit,
    onClickStart,
    onClickCreateShortcut,
  };
}
