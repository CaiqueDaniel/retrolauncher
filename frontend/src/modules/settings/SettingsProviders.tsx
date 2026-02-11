import React, { useMemo } from "react";
import { LocalSettingsGateway } from "./gateways/LocalSettingsGateway";
import { toast } from "react-toastify";
import { SettingsFormContext } from "./features/SettingsForm/SettingsFormContext";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";
import { FilepathSelectorContext } from "../shared/infra/features/FilepathSelector/FilepathSelectorContext";
import { LocalFolderPathSelectionService } from "../shared/infra/services/LocalFolderPathSelectionService";

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
      <FilepathSelectorContext.Provider
        value={{
          filepathSelectionService: new LocalFolderPathSelectionService(),
        }}
      >
        {children}
      </FilepathSelectorContext.Provider>
    </SettingsFormContext.Provider>
  );
}
