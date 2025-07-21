import { Box } from '@mui/material';
import { PropsWithChildren } from 'react';

export function MainLayout({ children }: PropsWithChildren) {
  return (
    <>
      <Box px={3} pt={3} bgcolor="#ededed" height="100vh">
        {children}
      </Box>
    </>
  );
}
