import { useEffect, useState } from "react";
import { useGameListContext } from "./GameListContext";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";

export function useGameListPresenter() {
  const { queryRepository, alert } = useGameListContext();
  const [games, setGames] = useState<GameQueryRepository.Output[]>([]);
  const [isLoading, setLoading] = useState(false);

  useEffect(fetchGames, []);

  return {
    games,
    isLoading,
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
