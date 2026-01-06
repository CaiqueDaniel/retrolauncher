import { Box, Button, Input, InputProps, TextField } from "@mui/material";
import { useFilepathSelectorPresenter } from "./useFilepathSelectorPresenter";

export function FilepathSelector(props: InputProps) {
    const { onClick, value } = useFilepathSelectorPresenter({ value: (props.value as string) || "" });
    return (
        <>
            <Box display='grid' gap={2} gridTemplateColumns='1fr 1fr' alignItems='center' sx={props.sx}>
                <Button variant="contained" color="secondary" onClick={onClick} sx={{ height: '55px' }}>
                    Selecionar arquivo
                </Button>
                <TextField value={value} disabled />
            </Box>
            <Input {...props} type='hidden' value={value} sx={{ display: 'none' }} />
        </>
    )
}