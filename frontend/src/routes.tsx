import { createBrowserRouter, Outlet } from "react-router-dom";
import { gamesRoutes } from "./modules/games/GamesRouter";
import { settingsRoutes } from "./modules/settings/SettingsRouter";
import { SharedProviders } from "./modules/shared/SharedProviders";

export const routes = createBrowserRouter([
  {
    path: "/",
    element: (
      <SharedProviders>
        <Outlet />
      </SharedProviders>
    ),
    children: [...gamesRoutes, ...settingsRoutes],
  },
]);
