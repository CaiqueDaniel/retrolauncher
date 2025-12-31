package get_game

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain/game"
)

type GetGame struct {
	Execute func(input Input) (*game.Game, error)
}

func New(repository game.GameRepository) *GetGame {
	return &GetGame{
		Execute: func(input Input) (*game.Game, error) { return execute(input, repository) },
	}
}

func execute(input Input, repository game.GameRepository) (*game.Game, error) {
	game, err := repository.Get(input.Id)

	if err != nil {
		return nil, err
	}

	if game == nil {
		return nil, errors.New("game not found")
	}

	return game, nil
}

type Input struct {
	Id string
}
