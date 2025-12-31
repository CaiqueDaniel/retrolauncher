package create_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

type CreateGame struct {
	Execute func(input Input) error
}

func New(factory game.GameFactory, repository game.GameRepository) *CreateGame {
	return &CreateGame{
		Execute: func(input Input) error { return execute(input, factory, repository) },
	}
}

func execute(input Input, factory game.GameFactory, repository game.GameRepository) error {
	var err error

	game := factory.CreateGame(input.Name, input.Platform, input.Path, input.Cover)

	if game == nil {
		return errors.New("failed to create game")
	}

	err = repository.Save(game)
	return err
}

type Input struct {
	Name     string
	Platform string
	Path     string
	Cover    string
}
