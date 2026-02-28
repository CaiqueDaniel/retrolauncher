import { Box, IconButton, Typography } from "@mui/material";
import { useGameViewerPresenter } from "./useGameViewerPresenter";
import { HideImage } from "@mui/icons-material";
import ShortcutIcon from '@mui/icons-material/Shortcut';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import EditIcon from '@mui/icons-material/Edit';

export function GameViewer() {
  const { game, onClickEdit, onClickStart, onClickCreateShortcut } = useGameViewerPresenter();

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
            <IconButton color="success" onClick={onClickStart} title="Iniciar jogo">
              <PlayArrowIcon />
            </IconButton>
            <IconButton color="secondary" onClick={onClickEdit} title="Editar jogo">
              <EditIcon />
            </IconButton>
            <IconButton color="secondary" onClick={onClickCreateShortcut} title="Criar atalho na área de trabalho">
              <ShortcutIcon />
            </IconButton>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}
