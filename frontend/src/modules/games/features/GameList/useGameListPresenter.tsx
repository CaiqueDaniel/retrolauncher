import { useEffect, useState } from "react";
import { useGameListContext } from "./GameListContext";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { GameEvents } from "../../GameEvents";
import { useBusSubscriber } from "~/modules/shared/infra/hooks/useBusSubscriber";

export function useGameListPresenter() {
  const { queryRepository, alert, busDispatcher, busSubscriber } = useGameListContext();
  const [games, setGames] = useState<GameQueryRepository.Output[]>([]);
  const [isLoading, setLoading] = useState(false);
  const [wasFirstLoaded, setWasFirstLoad] = useState(false);

  const onClick = (id: string) => {
    const game = games.find((game) => game.id === id);
    if (!game) return;
    busDispatcher.dispatch(GameEvents.GAME_SELECTED, game);
  };

  useEffect(fetchGames, []);
  useEffect(onInit, [wasFirstLoaded]);

  useBusSubscriber({
    bus: busSubscriber,
    eventName: GameEvents.GAME_LIST_REFRESH_REQUESTED,
    handler: fetchGames
  });

  return {
    games,
    isLoading,
    onClick,
  };

  function fetchGames() {
    setLoading(true);
    queryRepository
      .search({})
      .then(setGames)
      .catch(() => alert.error("Não foi possível carregar os jogos"))
      .finally(() => {
        setLoading(false);
        setWasFirstLoad(true);
      });
  }

  function onInit() {
    if (games[0]) onClick(games[0].id);
  }
}
