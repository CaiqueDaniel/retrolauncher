package update_game

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
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

	game.Update(
		input.Name,
		platform.New(input.PlatformType, input.PlatformPath),
		input.Path,
		input.Cover,
	)

	return repository.Save(game)
}

type Input struct {
	ID           string
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
