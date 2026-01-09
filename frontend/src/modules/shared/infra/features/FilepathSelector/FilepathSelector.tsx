import { Box, Button, FormControl, FormHelperText, Input, InputProps, TextField } from "@mui/material";
import { useFilepathSelectorPresenter } from "./useFilepathSelectorPresenter";

export function FilepathSelector(props: Props) {
    const { onClick, value } = useFilepathSelectorPresenter({ value: (props.value as string) || "" });
    return (
        <FormControl fullWidth sx={props.sx}>
            <Box display='grid' gap={1} gridTemplateColumns='200px 1fr' alignItems='center'>
                <Button variant="contained" color="secondary" onClick={onClick} sx={{ height: '55px' }}>
                    Selecionar arquivo
                </Button>
                <TextField value={value} label={`${props.label} ${props.required ? "*" : ""}`} disabled />
            </Box>
            <FormHelperText>{props.helperText}</FormHelperText>
            <Input {...props} type='hidden' value={value} sx={{ display: 'none' }} />
        </FormControl>

    )
}

type Props = InputProps & {
    helperText?: string;
    label: string;
}
