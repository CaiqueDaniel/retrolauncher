import {
  List,
  ListItem,
  ListItemButton,
  ListItemText,
} from "@mui/material";
import { useGameListPresenter } from "./useGameListPresenter";

export function GameList() {
  const { games, isLoading, selectedGameId, onClick } = useGameListPresenter();

  if (isLoading) return <></>

  return (
    <nav>
      <List>
        {
          games.map(game => (
            <ListItem disablePadding key={game.id}>
              <ListItemButton onClick={() => onClick(game.id)} selected={game.id === selectedGameId}>
                <ListItemText primary={game.name} />
              </ListItemButton>
            </ListItem>
          ))
        }
      </List>
    </nav>
  );
}
