import { Box, Modal as MUIModel, SxProps } from '@mui/material';
import { useModalPresenter } from './useModalPresenter';

export function Modal({ children, event }: Props) {
  const { show, message, onClose } = useModalPresenter({ event });

  return (
    <MUIModel open={show} onClose={onClose}>
      <Box sx={styles}>{children({ onClose, message })}</Box>
    </MUIModel>
  );
}

type Props = {
  children: (props: { onClose: () => void; message?: any }) => React.ReactNode;
  event: string;
};

const styles: SxProps = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '100%',
  maxWidth: 800,
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 3,
};
