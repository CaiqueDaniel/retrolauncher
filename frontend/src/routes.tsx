import { createBrowserRouter } from "react-router-dom";
import { gamesRoutes } from "./modules/games/GamesRouter";
import { platformsRoutes } from "./modules/platforms/PlatformsRouter";

export const routes = createBrowserRouter([...gamesRoutes, ...platformsRoutes]);
