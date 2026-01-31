import { Typography } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { SettingsFormData } from "../../services/SettingsGateway";
import { useSettingsFormPresenter } from "./useSettingsFormPresenter";
import { FilepathSelector } from "~/modules/shared/infra/features/FilepathSelector/FilepathSelector";
import { FormSubmitControls } from "~/modules/shared/infra/components/FormSubmitControls";

export function SettingsForm() {
  const {
    onSubmit,
    onCancel,
    isSubmiting,
    initialValues,
    validationSchema,
    isLoading,
  } = useSettingsFormPresenter();

  if (isLoading) return <></>;

  return (
    <Form<SettingsFormData>
      initialValues={initialValues}
      onSubmit={onSubmit}
      validationSchema={validationSchema}
    >
      {({ errors }) => (
        <>
          <Typography variant="h1" mb={3}>
            Configurações
          </Typography>

          <FastField
            as={FilepathSelector}
            name="retroarchFolderPath"
            label="Caminho do Retroarch"
            required
            fullWidth
            helperText={errors.retroarchFolderPath}
            error={Boolean(errors.retroarchFolderPath)}
            sx={{ mb: 2 }}
          />

          <FastField
            as={FilepathSelector}
            name="romsFolderPath"
            label="Caminho da Pasta de ROMs"
            required
            fullWidth
            helperText={errors.romsFolderPath}
            error={Boolean(errors.romsFolderPath)}
            sx={{ mb: 2 }}
          />

          <FormSubmitControls isSubmiting={isSubmiting} onCancel={onCancel} />
        </>
      )}
    </Form>
  );
}
