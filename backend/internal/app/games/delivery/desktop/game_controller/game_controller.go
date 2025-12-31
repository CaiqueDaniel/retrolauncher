package game_controller

import (
	"retrolauncher/backend/internal/app/games/application/create_game"
	"retrolauncher/backend/internal/app/games/application/list_games"
	"retrolauncher/backend/internal/app/games/application/update_game"
)

type GameController struct {
	Create func(input CreateInputDto) error
	Update func(input UpdateInputDto) error
	//Get    func()
	List func(input ListInputDto) []list_games.Output
}

func New(
	createGame *create_game.CreateGame,
	updateGame *update_game.UpdateGame,
	//getGame get_game.GetGame,
	listGames *list_games.ListGames,
) *GameController {
	return &GameController{
		Create: func(input CreateInputDto) error { return create(input, createGame) },
		Update: func(input UpdateInputDto) error { return update(input, updateGame) },
		List:   func(input ListInputDto) []list_games.Output { return list(input, listGames) },
	}
}

func create(input CreateInputDto, useCase *create_game.CreateGame) error {
	return useCase.Execute(create_game.Input{
		Name:     input.Name,
		Platform: input.Platform,
		Path:     input.Path,
		Cover:    input.Cover,
	})
}

func update(input UpdateInputDto, useCase *update_game.UpdateGame) error {
	return useCase.Execute(update_game.Input{
		ID:       input.ID,
		Name:     input.Name,
		Platform: input.Platform,
		Path:     input.Path,
		Cover:    input.Cover,
	})
}

func list(input ListInputDto, useCase *list_games.ListGames) []list_games.Output {
	return useCase.Execute(list_games.Input{
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
