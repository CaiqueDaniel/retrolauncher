import { Box } from "@mui/material";
import { PropsWithChildren } from "react";

export function MainLayout({ children }: PropsWithChildren) {
  return (
    <Box bgcolor="background.default" height="100vh" width="100vw">
      <Box p={3}>{children}</Box>
    </Box>
  );
}
