import {
  Box,
  Button,
  FormControl,
  FormHelperText,
  InputProps,
  TextField,
} from "@mui/material";
import { useFilepathSelectorPresenter } from "./useFilepathSelectorPresenter";
import { useEffect } from "react";
import { useFormikContext } from "formik";

export function FilepathSelector(props: Props) {
  const { setFieldValue } = useFormikContext();
  const { onClick, value } = useFilepathSelectorPresenter({
    value: (props.value as string) || "",
    extensions: props.extensions,
  });

  useEffect(() => {
    if (props.name) setFieldValue(props.name, value);
  }, [value]);

  return (
    <FormControl fullWidth sx={props.sx}>
      <Box
        display="grid"
        gap={1}
        gridTemplateColumns="200px 1fr"
        alignItems="center"
      >
        <Button
          variant="contained"
          color="secondary"
          onClick={onClick}
          sx={{ height: "55px" }}
        >
          Selecionar arquivo
        </Button>
        <TextField
          value={value}
          label={`${props.label} ${props.required ? "*" : ""}`}
          disabled
        />
      </Box>
      <FormHelperText>{props.helperText}</FormHelperText>
    </FormControl>
  );
}

type Props = InputProps & {
  helperText?: string;
  label: string;
  extensions?: string[];
};
