package game_controller

import (
	"retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/list_games"
	"retrolauncher/backend/src/app/games/application/update_game"
)

type GameController struct {
	createGame *create_game.CreateGame
	updateGame *update_game.UpdateGame
	listGames  *list_games.ListGames
}

func New(
	createGame *create_game.CreateGame,
	updateGame *update_game.UpdateGame,
	//getGame get_game.GetGame,
	listGames *list_games.ListGames,
) *GameController {
	return &GameController{
		createGame: createGame,
		updateGame: updateGame,
		listGames:  listGames,
	}
}

func (gc *GameController) Create(input CreateInputDto) error {
	return gc.createGame.Execute(create_game.Input{
		Name:     input.Name,
		Platform: input.Platform,
		Path:     input.Path,
		Cover:    input.Cover,
	})
}

func (gc *GameController) Update(input UpdateInputDto) error {
	return gc.updateGame.Execute(update_game.Input{
		ID:       input.ID,
		Name:     input.Name,
		Platform: input.Platform,
		Path:     input.Path,
		Cover:    input.Cover,
	})
}

func (gc *GameController) List(input ListInputDto) []*list_games.Output {
	return gc.listGames.Execute(list_games.Input{
		Name: input.Name,
	})
}

type CreateInputDto struct {
	Name     string `json:"name"`
	Platform string `json:"platform"`
	Path     string `json:"path"`
	Cover    string `json:"cover"`
}

type UpdateInputDto struct {
	ID string `json:"id"`
	CreateInputDto
}

type ListInputDto struct {
	Name string `json:"name"`
}
