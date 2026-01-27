package application

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
)

type UpdateGame interface {
	Execute(input UpdateGameInput) error
}

type updateGame struct {
	repository domain.GameRepository
}

func NewUpdateGame(repository domain.GameRepository) UpdateGame {
	return &updateGame{
		repository: repository,
	}
}

func (u *updateGame) Execute(input UpdateGameInput) error {
	game, err := u.repository.Get(input.ID)

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

	return u.repository.Save(game)
}

type UpdateGameInput struct {
	ID           string
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
