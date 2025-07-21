import { Box, CircularProgress } from '@mui/material';

export function LoadingScreen() {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      width="100vw"
      height="100vh"
    >
      <CircularProgress />
    </Box>
  );
}
