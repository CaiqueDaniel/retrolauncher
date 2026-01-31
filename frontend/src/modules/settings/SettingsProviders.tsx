import React, { useMemo } from "react";
import { LocalSettingsGateway } from "./gateways/LocalSettingsGateway";
import { toast } from "react-toastify";
import { SettingsFormContext } from "./features/SettingsForm/SettingsFormContext";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";

interface SettingsProvidersProps {
  children: React.ReactNode;
}

export function SettingsProviders({ children }: SettingsProvidersProps) {
  const gateway = useMemo(() => new LocalSettingsGateway(), []);

  return (
    <SettingsFormContext.Provider
      value={{
        gateway,
        alert: toast,
        navigator: useReactRouterRouteNavigator(),
      }}
    >
      {children}
    </SettingsFormContext.Provider>
  );
}
