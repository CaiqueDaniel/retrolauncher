import React from "react";
import { LocalSettingsGateway } from "./gateways/LocalSettingsGateway";
import { toast } from "react-toastify";
import { SettingsFormContext } from "./features/SettingsForm/SettingsFormContext";

interface SettingsProvidersProps {
  children: React.ReactNode;
}

export function SettingsProviders({ children }: SettingsProvidersProps) {
  const gateway = new LocalSettingsGateway();

  return (
    <SettingsFormContext.Provider value={{ gateway, alert: toast }}>
      {children}
    </SettingsFormContext.Provider>
  );
}
