import { SettingsForm } from "../features/SettingsForm/SettingsForm";
import { FormLayout } from "~/modules/shared/layouts/FormLayout";

export function SettingsPage() {
  return (
    <FormLayout>
      <SettingsForm />
    </FormLayout>
  );
}
