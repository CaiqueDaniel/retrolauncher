import { Typography, TextField, MenuItem } from "@mui/material";
import { FastField } from "formik";
import { Form } from "~/modules/shared/infra/components/Form/Form";
import { PlatformFormData } from "./PlatformFormData";
import { FormSubmitControls } from "./FormSubmitControls";
import { usePlatformFormPresenter } from "./usePlatformFormPresenter";
import { FilepathSelector } from "~/modules/shared/infra/features/FilepathSelector/FilepathSelector";

export function PlatformForm() {
    const {
        onSubmit,
        onCancel,
        isSubmiting,
        isLoading,
        initialValues,
        validationSchema,
        platformTypes
    } = usePlatformFormPresenter();

    if (isLoading) return <></>;

    return (
        <Form<PlatformFormData>
            initialValues={initialValues}
            onSubmit={onSubmit}
            validationSchema={validationSchema}
        >
            {({ errors }) => (
                <>
                    <Typography variant="h1" gutterBottom>
                        Plataforma
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
                        name="type"
                        label="Tipo de emulador"
                        required
                        fullWidth
                        helperText={errors.type}
                        error={Boolean(errors.type)}
                        sx={{ mb: 2 }}
                    >
                        {platformTypes.map((type) => (
                            <MenuItem key={type} value={type}>
                                {type}
                            </MenuItem>
                        ))}
                    </FastField>

                    <FastField
                        as={FilepathSelector}
                        name='path'
                        label="Caminho do núcleo"
                        required
                        fullWidth
                        helperText={errors.path}
                        error={Boolean(errors.path)}
                        sx={{ mb: 2 }}
                    />
                    <FormSubmitControls onCancel={onCancel} isSubmiting={isSubmiting} />
                </>
            )}
        </Form>
    )
}
