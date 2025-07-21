import { useState } from 'react';

export function useMenuPresenter() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleClick = (event: React.MouseEvent<HTMLElement>) =>
    setAnchorEl(event.currentTarget);
  
  const handleClose = () => setAnchorEl(null);

  return { open, handleClick, handleClose, anchorEl };
}
