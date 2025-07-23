import { RouteObject } from "react-router-dom";
import { GameHome } from "./pages/GameHome";

export const gamesRoutes: RouteObject[] = [
  {
    path: "/",
    element: <GameHome />,
  },
];
