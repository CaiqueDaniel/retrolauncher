import { useEffect, useState } from "react";
import { useGameListContext } from "./GameListContext";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { GameEvents } from "../../GameEvents";

export function useGameListPresenter() {
  const { queryRepository, alert, busDispatcher } = useGameListContext();
  const [games, setGames] = useState<GameQueryRepository.Output[]>([]);
  const [isLoading, setLoading] = useState(false);

  const onClick = (id: string) => {
    const game = games.find(game => game.id === id);
    if (!game) return;
    busDispatcher.dispatch(GameEvents.GAME_SELECTED, game);
  }

  useEffect(fetchGames, []);

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
      .finally(() => setLoading(false));
  }
}
