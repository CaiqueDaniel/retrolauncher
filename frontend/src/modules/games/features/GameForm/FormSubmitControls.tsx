import { Box, Button } from '@mui/material';

export function FormSubmitControls({
    isSubmiting,
    submitBtnLabel = 'Concluir',
    onCancel,
}: Props) {
    return (
        <Box display="grid" gridTemplateColumns="1fr 1fr" columnGap={3}>
            <Button color="error" variant="contained" size="large" onClick={onCancel}>
                Cancelar
            </Button>

            <Button
                type="submit"
                color="primary"
                variant="contained"
                size="large"
                disabled={isSubmiting}
            >
                {submitBtnLabel}
            </Button>
        </Box>
    );
}

type Props = {
    isSubmiting?: boolean;
    submitBtnLabel?: string;
    onCancel: () => void;
};