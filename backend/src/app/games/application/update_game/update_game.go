package update_game

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
)

type UpdateGame struct {
	Execute func(input Input) error
}

func New(repository domain.GameRepository) *UpdateGame {
	return &UpdateGame{
		Execute: func(input Input) error { return execute(input, repository) },
	}
}

func execute(input Input, repository domain.GameRepository) error {
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

type Input struct {
	ID       string
	Name     string
	Platform string
	Path     string
	Cover    string
}
