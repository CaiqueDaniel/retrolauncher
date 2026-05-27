import { useBusSubscriber } from "~/modules/shared/infra/hooks/useBusSubscriber";
import { useGameViewerContext } from "./GameViewerContext";
import { GameEvents } from "../../GameEvents";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { useEffect, useState } from "react";
import { Achievement } from "../../services/AchivementsService";

export function useGameViewerPresenter() {
  const { busSubscriber, routeNavigate, startGameService, gameShortcutService, alert, achievementsService } =
    useGameViewerContext();
  const [game, setGame] = useState<GameQueryRepository.Output | null>(null);
  const [achivements, setAchievements] = useState<Achievement[]>([]);

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

  useEffect(() => {
    setAchievements([]);

    if (!game) return;

    achievementsService.listAchievementsFromGame(game.id)
      .then(setAchievements)
      .catch(alert.error);
  }, [game?.id]);

  return {
    game,
    achivements,
    onClickEdit,
    onClickStart,
    onClickCreateShortcut,
  };
}
