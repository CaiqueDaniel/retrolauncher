package create_game

import (
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
)

type CreateGame struct {
	Execute func(input Input) []error
}

func New(factory domain.GameFactory, repository domain.GameRepository) *CreateGame {
	return &CreateGame{
		Execute: func(input Input) []error { return execute(input, factory, repository) },
	}
}

func execute(input Input, factory domain.GameFactory, repository domain.GameRepository) []error {
	game, gameErrors := factory.CreateGame(
		input.Name,
		platform.New(input.PlatformType, input.PlatformPath),
		input.Path,
		input.Cover,
	)

	if game == nil {
		return gameErrors
	}

	if len(gameErrors) > 0 {
		return gameErrors
	}

	repoErr := repository.Save(game)

	if repoErr != nil {
		return []error{repoErr}
	}

	return nil
}

type Input struct {
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
