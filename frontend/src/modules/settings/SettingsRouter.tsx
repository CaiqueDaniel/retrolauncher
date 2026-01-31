import { RouteObject } from "react-router-dom";
import { SettingsPage } from "./pages/SettingsPage";
import { SettingsProviders } from "./SettingsProviders";

export const settingsRoutes: RouteObject[] = [
  {
    path: "/settings",
    element: (
      <SettingsProviders>
        <SettingsPage />
      </SettingsProviders>
    ),
  },
];
