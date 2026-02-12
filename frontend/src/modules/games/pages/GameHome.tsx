import { GameList } from "../features/GameList/GameList";
import { Box, Button, Paper } from "@mui/material";
import { GameViewer } from "../features/GameViewer/GameViewer";
import { MainLayout } from "~/modules/shared/layouts/MainLayout";
import { useReactRouterRouteNavigator } from "~/modules/shared/infra/hooks/useReactRouterRouteNavigator";
import { IndexGamesButton } from "../features/IndexGamesButton/IndexGamesButton";
import { Add, Settings } from "@mui/icons-material";

export function GameHome() {
  const routeNavigator = useReactRouterRouteNavigator();

  return (
    <MainLayout>
      <Box display="flex" gap={2} justifyContent="flex-end">
        <IndexGamesButton />
        <Button
          variant="contained"
          onClick={() => routeNavigator.navigateTo("/settings")}
        >
          <Settings />
        </Button>
        <Button
          variant="contained"
          color="success"
          onClick={() => routeNavigator.navigateTo("/game/new")}
        >
          <Add />
        </Button>
      </Box>

      <Box
        display="grid"
        gridTemplateColumns="1fr 2fr"
        gap={2}
        bgcolor="background"
        py={2}
        height='calc(100% - 48px)'
      >
        <Paper sx={{ height: "100%", overflow: "auto" }}>
          <GameList />
        </Paper>
        <Paper>
          <GameViewer />
        </Paper>
      </Box>
    </MainLayout>
  );
}
