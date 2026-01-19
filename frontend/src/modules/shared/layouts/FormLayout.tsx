import { Box, Paper } from "@mui/material";
import { PropsWithChildren } from "react";

export function FormLayout({ children }: PropsWithChildren) {
    return (
        <Box bgcolor="background.default" height="100vh" width="100vw">
            <Box display="flex" justifyContent="center" alignItems="center" height="100%">
                <Paper sx={{ p: 3, mb: 3, maxWidth: 800 }}>
                    {children}
                </Paper>
            </Box>
        </Box>
    )
}