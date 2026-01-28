import { GameList } from "../features/GameList/GameList";
import { Box, Button, Paper } from "@mui/material";
import { GameViewer } from "../features/GameViewer/GameViewer";
import { MainLayout } from "~/modules/shared/layouts/MainLayout";
import { useReactRouterRouteNavigator } from "~/modules/shared/infra/hooks/useReactRouterRouteNavigator";

export function GameHome() {
  const routeNavigator = useReactRouterRouteNavigator();

  return (
    <MainLayout>
      <Box display="flex" gap={2} justifyContent="flex-end">
        <Button
          variant="contained"
          onClick={() => routeNavigator.navigateTo("/game/new")}
        >
          Adicionar Jogo
        </Button>
      </Box>

      <Box
        display="grid"
        gridTemplateColumns="1fr 2fr"
        gap={2}
        bgcolor="background"
        p={2}
        height='calc(100% - 48px)'
      >
        <Paper>
          <GameList />
        </Paper>
        <Paper>
          <GameViewer />
        </Paper>
      </Box>
    </MainLayout>
  );
}
