import { useState } from "react";
import { useIndexGamesButtonContext } from "./IndexGamesButtonContext";

export function useIndexGamesButtonPresenter() {
    const { indexGamesService, alert } = useIndexGamesButtonContext();
    const [loading, setLoading] = useState(false);

    const handleIndexGames = async () => {
        setLoading(true);

        await indexGamesService.indexGames()
            .then(() => alert.success("Jogos indexados com sucesso"))
            .catch((error) => alert.error(`Erro ao indexar jogos: ${error}`))
            .finally(() => setLoading(false));
    };

    return {
        handleIndexGames,
        loading,
    };
}