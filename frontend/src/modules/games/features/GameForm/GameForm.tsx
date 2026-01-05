import { TextField } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { GameFormData } from "./GameFormData";
import { FormSubmitControls } from "./FormSubmitControls";
import { useGameFormPresenter } from "./useGameFormPresenter";

export function GameForm() {
    const { onSubmit, onCancel, isSubmiting, initialValues, validationSchema } = useGameFormPresenter();

    return (
        <Form<GameFormData>
            initialValues={initialValues}
            onSubmit={onSubmit}
            validationSchema={validationSchema}
        >
            {({ errors }) => (
                <>
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
                        name="platform"
                        label="Plataforma"
                        required
                        fullWidth
                        helperText={errors.name}
                        error={Boolean(errors.name)}
                        sx={{ mb: 2 }}
                    />

                    <FastField
                        as={TextField}
                        name="path"
                        label="Caminho"
                        required
                        fullWidth
                        helperText={errors.name}
                        error={Boolean(errors.name)}
                        sx={{ mb: 2 }}
                    />
                    <FastField
                        as={TextField}
                        name="cover"
                        label="Capa"
                        required
                        fullWidth
                        helperText={errors.name}
                        error={Boolean(errors.name)}
                        sx={{ mb: 2 }}
                    />
                    <FormSubmitControls onCancel={onCancel} isSubmiting={isSubmiting} />
                </>
            )}
        </Form>
    )
}