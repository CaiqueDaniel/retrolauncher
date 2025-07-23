import { createBrowserRouter } from "react-router-dom";
import { gamesRoutes } from "./modules/games/GamesRouter";

export const routes = createBrowserRouter([...gamesRoutes]);
