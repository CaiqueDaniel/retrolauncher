package application

import (
	"errors"
	"retrolauncher/backend/src/app/games/internal/domain"
)

type StartGame interface {
	Execute(input StartGameInput) error
}

type startGame struct {
	repository       domain.GameRepository
	startGameService StartGameService
}

func NewStartGame(repository domain.GameRepository, startGameService StartGameService) StartGame {
	return &startGame{
		repository:       repository,
		startGameService: startGameService,
	}
}

func (s *startGame) Execute(input StartGameInput) error {
	game, err := s.repository.Get(input.GameId)

	if err != nil {
		return err
	}

	if game == nil {
		return errors.New("game not found")
	}

	return s.startGameService.StartGame(game.GetPath(), game.GetPlatformType().GetPath())
}

type StartGameInput struct {
	GameId string
}
