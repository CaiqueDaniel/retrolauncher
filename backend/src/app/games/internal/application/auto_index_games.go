package application

import "retrolauncher/backend/src/app/games/internal/domain"

type AutoIndexGames interface {
	Execute() error
}

type useCase struct {
	repository domain.GameRepository
}

func NewAutoIndexGames(repository domain.GameRepository) AutoIndexGames {
	return &useCase{
		repository: repository,
	}
}

func (uc *useCase) Execute() error {

	return nil
}
