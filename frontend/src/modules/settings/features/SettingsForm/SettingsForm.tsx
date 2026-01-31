import { Typography, Box, Button } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { SettingsFormData } from "../../services/SettingsGateway";
import { useSettingsFormPresenter } from "./useSettingsFormPresenter";
import { FilepathSelector } from "~/modules/shared/infra/features/FilepathSelector/FilepathSelector";

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

          <Box display="flex" gap={2} mt={3}>
            <Button
              type="submit"
              variant="contained"
              color="secondary"
              onClick={onCancel}
            >
              Voltar
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              disabled={isSubmiting}
            >
              Salvar
            </Button>
          </Box>
        </>
      )}
    </Form>
  );
}
