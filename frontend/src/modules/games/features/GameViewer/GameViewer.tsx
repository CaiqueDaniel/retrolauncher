import { Box, Button, Typography } from "@mui/material";
import { useGameViewerPresenter } from "./useGameViewerPresenter";
import { HideImage } from "@mui/icons-material";

export function GameViewer() {
  const { game, onClickEdit, onClickStart } = useGameViewerPresenter();

  if (!game) return <></>;

  return (
    <Box p={3}>
      <Box display="flex" gap={2}>
        {
          game.cover.length ?
            <img src={`data:image/jpeg;base64,${game.cover}`} width="100px" /> :
            <HideImage sx={{ fontSize: 85 }} />
        }
        <Box>
          <Typography variant="h5" mb={1}>
            {game?.name}
          </Typography>
          <Box display="flex" gap={1}>
            <Button variant="contained" color="success" onClick={onClickStart}>
              Iniciar
            </Button>
            <Button onClick={onClickEdit}>Editar</Button>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}
