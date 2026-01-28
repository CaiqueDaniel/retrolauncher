import { createTheme } from "@mui/material";

export const theme = createTheme({
    palette: {
        mode: "dark",
    },
    typography: {
        h1: {
            fontSize: '1.70rem',
            lineHeight: '2.5rem',
            fontWeight: '700',
        },
        h2: {
            fontSize: '1.5rem',
            lineHeight: '2rem',
            fontWeight: '600',
        },
        h3: {
            fontSize: '1.25rem',
            lineHeight: '1.75rem',
            fontWeight: '500',
        },
        h4: {
            fontSize: '1rem',
            lineHeight: '1.5rem',
            fontWeight: '500',
        },
    }
})