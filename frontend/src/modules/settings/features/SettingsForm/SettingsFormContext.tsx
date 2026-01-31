import { createContext } from "react";
import { SettingsService } from "../../services/SettingsGateway";
import { useContextHandler } from "~/modules/shared/infra/hooks/useContextHandler";
import { Alert } from "~/modules/shared/application/Alert";
import { RouteNavigator } from "~/modules/shared/application/RouteNavigator";

export const SettingsFormContext = createContext<Context | undefined>(
  undefined,
);

export function useSettingsFormContext() {
  return useContextHandler(SettingsFormContext);
}

type Context = {
  gateway: SettingsService;
  alert: Alert;
  navigator: RouteNavigator;
};
