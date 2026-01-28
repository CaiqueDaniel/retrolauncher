package application

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
	"retrolauncher/backend/src/shared/application"
)

type UpdateGame interface {
	Execute(input UpdateGameInput) []error
}

type updateGame struct {
	repository    domain.GameRepository
	imageUploader application.ImageUploader
}

func NewUpdateGame(repository domain.GameRepository, imageUploader application.ImageUploader) UpdateGame {
	return &updateGame{
		repository:    repository,
		imageUploader: imageUploader,
	}
}

func (u *updateGame) Execute(input UpdateGameInput) []error {
	game, err := u.repository.Get(input.ID)

	if err != nil {
		return []error{err}
	}

	if game == nil {
		return []error{errors.New("game not found")}
	}

	coverPath, coverCopyError := u.imageUploader.CopyImageToInternal(input.Cover)

	if coverCopyError != nil {
		return []error{coverCopyError}
	}

	entityErrors := game.Update(
		input.Name,
		platform.New(input.PlatformType, input.PlatformPath),
		input.Path,
		coverPath,
	)

	if len(entityErrors) > 0 {
		u.imageUploader.RollbackCopy(coverPath)
		return entityErrors
	}

	err = u.repository.Save(game)

	if err != nil {
		u.imageUploader.RollbackCopy(coverPath)
		return []error{err}
	}

	return nil
}

type UpdateGameInput struct {
	ID           string
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
