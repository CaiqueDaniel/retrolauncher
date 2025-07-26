package application_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

func CreateGame(input CreateGameInput, factory game.GameFactory, repository game.GameRepository) error {
	var err error

	game := factory.CreateGame(input.Name, input.Platform, input.Path, input.Cover)

	if game == nil {
		return errors.New("failed to create game")
	}

	err = repository.Save(game)
	return err
}

type CreateGameInput struct {
	Name     string
	Platform string
	Path     string
	Cover    string
}
