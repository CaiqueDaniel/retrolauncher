import {
  List,
  ListItem,
  ListItemButton,
  ListItemText,
} from "@mui/material";

export function GameList() {
  return (
    <nav>
      <List>
        <ListItem disablePadding>
          <ListItemButton>
            <ListItemText primary="Teste" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton>
            <ListItemText primary="Test 2" />
          </ListItemButton>
        </ListItem>
      </List>
    </nav>
  );
}
