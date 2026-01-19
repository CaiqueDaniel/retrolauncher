package start_game

import (
	"errors"
	"retrolauncher/backend/src/app/games/application"
	"retrolauncher/backend/src/app/games/domain"
)

type StartGame struct {
	Execute func(input Input) error
}

func New(repository domain.GameRepository, startGameService application.StartGameService) *StartGame {
	return &StartGame{
		Execute: func(input Input) error {
			return execute(input, repository, startGameService)
		},
	}
}

func execute(
	input Input,
	repository domain.GameRepository,
	startGameService application.StartGameService,
) error {
	game, err := repository.Get(input.GameId)

	if err != nil {
		return err
	}

	if game == nil {
		return errors.New("game not found")
	}

	return startGameService.StartGame(game.GetPath(), game.GetPlatformType().GetPath())
}

type Input struct {
	GameId string
}
