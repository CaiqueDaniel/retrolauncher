import { Outlet, RouteObject } from "react-router-dom";
import { PlatformsProviders } from "./PlatformsProviders";
import { PlatformFormPage } from "./pages/PlatformFormPage";

export const platformsRoutes: RouteObject[] = [
  {
    path: "platforms",
    element: (
      <PlatformsProviders>
        <Outlet />
      </PlatformsProviders>
    ),
    children: [
      {
        path: "new",
        element: <PlatformFormPage />
      }
    ]
  },
];
