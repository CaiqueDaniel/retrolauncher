package application

import (
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
	"retrolauncher/backend/src/shared/application"
)

type CreateGame interface {
	Execute(input CreateGameInput) []error
}

type createGame struct {
	factory       domain.GameFactory
	repository    domain.GameRepository
	imageUploader application.ImageUploader
}

func NewCreateGame(
	factory domain.GameFactory,
	repository domain.GameRepository,
	imageUploader application.ImageUploader,
) CreateGame {
	return &createGame{
		factory:       factory,
		repository:    repository,
		imageUploader: imageUploader,
	}
}

func (c *createGame) Execute(input CreateGameInput) []error {
	coverPath, coverCopyError := c.imageUploader.CopyImageToInternal(input.Cover)

	if coverCopyError != nil {
		return []error{coverCopyError}
	}

	game, gameErrors := c.factory.CreateGame(
		input.Name,
		platform.New(input.PlatformType, input.PlatformPath),
		input.Path,
		coverPath,
	)

	if game == nil || len(gameErrors) > 0 {
		c.imageUploader.RollbackCopy(coverPath)
		return gameErrors
	}

	repoErr := c.repository.Save(game)

	if repoErr != nil {
		c.imageUploader.RollbackCopy(coverPath)
		return []error{repoErr}
	}

	return nil
}

type CreateGameInput struct {
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
