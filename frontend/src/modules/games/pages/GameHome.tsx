import { toast } from "react-toastify";
import { GameList } from "../features/GameList/GameList";
import { GameListContext } from "../features/GameList/GameListContext";
import { Box, Paper } from "@mui/material";
import { GameViewer } from "../features/GameViewer/GameViewer";
import { MainLayout } from "~/modules/shared/infra/layouts/MainLayout";

export function GameHome() {
  return (
    <MainLayout>
      <GameListContext.Provider
        value={{
          alert: toast,
          queryRepository: {
            search: async () => {
              return [];
            },
          },
        }}
      >
        <Box
          display="grid"
          gridTemplateColumns="1fr 2fr"
          gap={2}
          bgcolor="background"
          p={2}
        >
          <Paper>
            <GameList />
          </Paper>
          <Paper>
            <GameViewer />
          </Paper>
        </Box>
      </GameListContext.Provider>
    </MainLayout>
  );
}
