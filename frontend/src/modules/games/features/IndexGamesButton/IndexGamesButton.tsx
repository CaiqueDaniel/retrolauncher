import { Button } from "@mui/material";
import { useIndexGamesButtonPresenter } from "./useIndexGamesButtonPresenter";
import { PlaylistAdd } from "@mui/icons-material";

export function IndexGamesButton() {
    const { handleIndexGames, loading } = useIndexGamesButtonPresenter();

    return (
        <Button
            variant="contained"
            onClick={handleIndexGames}
            disabled={loading}
            loading={loading}
        >
            <PlaylistAdd />
        </Button>
    );
}