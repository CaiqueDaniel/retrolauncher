import { Box, Button, Typography } from "@mui/material";
import { useGameViewerPresenter } from "./useGameViewerPresenter";

export function GameViewer() {
  const { game } = useGameViewerPresenter();

  return (
    <Box p={3}>
      <Box display="flex" gap={2}>
        <img
          src="https://upload.wikimedia.org/wikipedia/pt/thumb/0/03/Super_Mario_Bros._box.png/260px-Super_Mario_Bros._box.png"
          width="100px"
        />
        <Box>
          <Typography variant="h5" mb={1}>
            {game?.name}
          </Typography>
          <Button variant="contained" color="success">
            Iniciar
          </Button>
        </Box>
      </Box>
    </Box>
  );
}
