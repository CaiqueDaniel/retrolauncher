import {
  List,
  ListItem,
  ListItemButton,
  ListItemText,
} from "@mui/material";
import { useGameListPresenter } from "./useGameListPresenter";

export function GameList() {
  const { games, isLoading, onClick } = useGameListPresenter();

  if (isLoading) return <></>

  return (
    <nav>
      <List>
        {
          games.map(game => (
            <ListItem disablePadding>
              <ListItemButton onClick={() => onClick(game.id)}>
                <ListItemText primary={game.name} />
              </ListItemButton>
            </ListItem>
          ))
        }
      </List>
    </nav>
  );
}
