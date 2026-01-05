import { TextField } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { PlatformFormData } from "./PlatformFormData";
import { FormSubmitControls } from "./FormSubmitControls";
import { usePlatformFormPresenter } from "./usePlatformFormPresenter";

export function PlatformForm() {
    const { onSubmit, onCancel, isSubmiting, initialValues, validationSchema } = usePlatformFormPresenter();

    return (
        <Form<PlatformFormData>
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
                        name="type"
                        label="Tipo de emulador"
                        required
                        fullWidth
                        helperText={errors.type}
                        error={Boolean(errors.type)}
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
                    <FormSubmitControls onCancel={onCancel} isSubmiting={isSubmiting} />
                </>
            )}
        </Form>
    )
}