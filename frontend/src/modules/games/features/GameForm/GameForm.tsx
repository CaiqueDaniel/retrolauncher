import { MenuItem, TextField, Typography } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { GameFormData } from "./GameFormData";
import { FormSubmitControls } from "../../../shared/infra/components/FormSubmitControls";
import { useGameFormPresenter } from "./useGameFormPresenter";
import { FilepathSelector } from "~/modules/shared/infra/features/FilepathSelector/FilepathSelector";

export function GameForm(props: Props) {
  const {
    onSubmit,
    onCancel,
    isSubmiting,
    initialValues,
    validationSchema,
    isLoadingPlatform,
    isLoadingGame,
    platforms,
  } = useGameFormPresenter(props);

  if (isLoadingPlatform || isLoadingGame) return <></>;

  return (
    <Form<GameFormData>
      initialValues={initialValues}
      onSubmit={onSubmit}
      validationSchema={validationSchema}
    >
      {({ errors }) => (
        <>
          <Typography variant="h1" mb={3}>
            {props.id ? "Editar" : "Adicionar"} Jogo
          </Typography>
          <FastField
            as={TextField}
            name="name"
            label="Nome"
            required
            fullWidth
            helperText={errors.name}
            error={Boolean(errors.name)}
            sx={{ mb: 2 }}
          />
          <FastField
            as={TextField}
            select
            name="platformType"
            label="Plataforma"
            required
            fullWidth
            helperText={errors.platformType}
            error={Boolean(errors.platformType)}
            sx={{ mb: 2 }}
          >
            {platforms.map((platform) => (
              <MenuItem key={platform} value={platform}>
                {platform}
              </MenuItem>
            ))}
          </FastField>

          <FastField
            as={FilepathSelector}
            name="platformPath"
            label="Caminho da Plataforma"
            required
            fullWidth
            helperText={errors.platformPath}
            error={Boolean(errors.platformPath)}
            sx={{ mb: 2 }}
          />

          <FastField
            as={FilepathSelector}
            name="path"
            label="Caminho"
            required
            fullWidth
            helperText={errors.path}
            error={Boolean(errors.path)}
            sx={{ mb: 2 }}
            extensions={[
              "*.iso",
              "*.bin",
              "*.cue",
              "*.nes",
              "*.sfc",
              "*.smc",
              "*.gb",
              "*.gba",
              "*.gbc",
              "*.n64",
              "*.v64",
              "*.z64",
            ]}
          />
          <FastField
            as={FilepathSelector}
            name="cover"
            label="Capa"
            required
            fullWidth
            helperText={errors.cover}
            error={Boolean(errors.cover)}
            sx={{ mb: 2 }}
            extensions={["*.png", "*.jpg", "*.jpeg"]}
          />
          <FormSubmitControls onCancel={onCancel} isSubmiting={isSubmiting} />
        </>
      )}
    </Form>
  );
}

type Props = {
  id?: string;
};
