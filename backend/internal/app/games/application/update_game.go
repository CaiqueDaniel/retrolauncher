package application_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

func UpdateGame(input UpdateGameInput, repository game.GameRepository) error {
	game, err := repository.Get(input.ID)

	if err != nil {
		return err
	}

	if game == nil {
		return errors.New("game not found")
	}

	game.Update(input.Name, input.Platform, input.Path, input.Cover)

	return repository.Save(game)
}

type UpdateGameInput struct {
	ID       string
	Name     string
	Platform string
	Path     string
	Cover    string
}
