import { Button } from "@mui/material";
import { useIndexGamesButtonPresenter } from "./useIndexGamesButtonPresenter";

export function IndexGamesButton() {
    const { handleIndexGames, loading } = useIndexGamesButtonPresenter();

    return (
        <Button
            variant="contained"
            onClick={handleIndexGames}
            disabled={loading}
            loading={loading}
        >
            Auto Indexar
        </Button>
    );
}