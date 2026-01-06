import { Box, Button, FormHelperText, Input, InputProps, TextField } from "@mui/material";
import { useFilepathSelectorPresenter } from "./useFilepathSelectorPresenter";

export function FilepathSelector(props: Props) {
    const { onClick, value } = useFilepathSelectorPresenter({ value: (props.value as string) || "" });
    return (
        <Box sx={props.sx} display='flex' flexDirection='column' alignItems='left'>
            <Box display='grid' gap={2} gridTemplateColumns='1fr 1fr' alignItems='center'>
                <Button variant="contained" color="secondary" onClick={onClick} sx={{ height: '55px' }}>
                    Selecionar arquivo
                </Button>
                <TextField value={value} disabled />
            </Box>
            <FormHelperText>{props.helperText}</FormHelperText>
            <Input {...props} type='hidden' value={value} sx={{ display: 'none' }} />
        </Box>
    )
}

type Props = InputProps & {
    helperText?: string;
}
