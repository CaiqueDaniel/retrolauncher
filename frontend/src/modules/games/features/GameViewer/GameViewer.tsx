import { Box, Card, CardContent, IconButton, Typography } from "@mui/material";
import { useGameViewerPresenter } from "./useGameViewerPresenter";
import { HideImage } from "@mui/icons-material";
import ShortcutIcon from '@mui/icons-material/Shortcut';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import EditIcon from '@mui/icons-material/Edit';

export function GameViewer() {
  const { game, achivements, onClickEdit, onClickStart, onClickCreateShortcut } = useGameViewerPresenter();

  if (!game) return <></>;

  return (
    <Box p={3} height="100%">
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

      <Box mt={2} display="grid" gridTemplateColumns="1fr 1fr" gap={3} overflow="scroll" height="100%">
        {
          achivements.map((achievement) => (
            <Card variant="outlined" sx={{
              opacity: achievement.dateEarned ? 1 : 0.3,
              overflow: "visible"
            }}>
              <CardContent>
                <Box key={achievement.id} display="flex" alignItems="center" gap={1}>
                  <Typography variant="body1" fontWeight="bold">{achievement.points}</Typography>
                  <Box>
                    <Typography variant="body1" fontWeight="bold">{achievement.title}</Typography>
                    <Typography variant="body2">{achievement.description}</Typography>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          ))
        }
      </Box>

    </Box>
  );
}
