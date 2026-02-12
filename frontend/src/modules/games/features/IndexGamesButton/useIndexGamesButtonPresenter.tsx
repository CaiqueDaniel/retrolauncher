import { useState } from "react";
import { useIndexGamesButtonContext } from "./IndexGamesButtonContext";
import { GameEvents } from "../../GameEvents";

export function useIndexGamesButtonPresenter() {
    const { indexGamesService, alert, busDispatcher } = useIndexGamesButtonContext();
    const [loading, setLoading] = useState(false);

    const handleIndexGames = async () => {
        setLoading(true);

        await indexGamesService.indexGames()
            .then(() => {
                alert.success("Jogos indexados com sucesso");
                busDispatcher.dispatch(GameEvents.GAME_LIST_REFRESH_REQUESTED);
            })
            .catch((error) => alert.error(error))
            .finally(() => setLoading(false));
    };

    return {
        handleIndexGames,
        loading,
    };
}