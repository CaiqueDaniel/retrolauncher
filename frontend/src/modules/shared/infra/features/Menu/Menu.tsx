import { PropsWithChildren, ReactNode } from 'react';
import { useMenuPresenter } from './useMenuPresenter';
import {
  IconButton,
  ListItemIcon,
  ListItemText,
  MenuItem,
  Menu as MUIMenu,
} from '@mui/material';
import { Edit, MoreVert } from '@mui/icons-material';

function Root({ children }: PropsWithChildren) {
  const { open, anchorEl, handleClick, handleClose } = useMenuPresenter();

  return (
    <>
      <IconButton onClick={handleClick}>
        <MoreVert />
      </IconButton>
      <MUIMenu
        id="long-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
      >
        {children}
      </MUIMenu>
    </>
  );
}

function Item({ label, icon, onClick }: PropsItem) {
  return (
    <MenuItem onClick={onClick}>
      <ListItemIcon>{icon}</ListItemIcon>
      <ListItemText>{label}</ListItemText>
    </MenuItem>
  );
}

function EditItem({ onClick }: OnClickProps) {
  return <Item label="Editar" icon={<Edit />} onClick={onClick} />;
}

export const Menu = Object.assign(Root, { Item, EditItem });

type PropsItem = OnClickProps & {
  icon: ReactNode;
  label: string;
};

type OnClickProps = {
  onClick: () => void;
};
