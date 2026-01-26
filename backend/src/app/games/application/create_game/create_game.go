package create_game

import (
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
	shared_application "retrolauncher/backend/src/shared/application"
)

type CreateGame struct {
	Execute func(input Input) []error
}

func New(
	factory domain.GameFactory,
	repository domain.GameRepository,
	imageUploader shared_application.ImageUploader,
) *CreateGame {
	return &CreateGame{
		Execute: func(input Input) []error { return execute(input, factory, repository, imageUploader) },
	}
}

func execute(
	input Input,
	factory domain.GameFactory,
	repository domain.GameRepository,
	imageUploader shared_application.ImageUploader,
) []error {
	coverPath, coverCopyError := imageUploader.CopyImageToInternal(input.Cover)

	if coverCopyError != nil {
		return []error{coverCopyError}
	}

	game, gameErrors := factory.CreateGame(
		input.Name,
		platform.New(input.PlatformType, input.PlatformPath),
		input.Path,
		coverPath,
	)

	if game == nil || len(gameErrors) > 0 {
		imageUploader.RollbackCopy(coverPath)
		return gameErrors
	}

	repoErr := repository.Save(game)

	if repoErr != nil {
		imageUploader.RollbackCopy(coverPath)
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
