import { Outlet, RouteObject } from "react-router-dom";
import { GameHome } from "./pages/GameHome";
import { GameProviders } from "./GameProviders";
import { GameFormPage } from "./pages/GameFormPage";

export const gamesRoutes: RouteObject[] = [
  {
    path: "/",
    element: (
      <GameProviders>
        <Outlet />
      </GameProviders>
    ),
    children: [
      {
        path: "/",
        element: <GameHome />
      },
      {
        path: "/new",
        element: <GameFormPage />
      }
    ]
  },
];
