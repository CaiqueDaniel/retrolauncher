package get_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

type GetGame struct {
	repository game.GameRepository
}

func New(repository game.GameRepository) *GetGame {
	return &GetGame{
		repository: repository,
	}
}

func (gg *GetGame) Execute(input Input) (*game.Game, error) {
	game, err := gg.repository.Get(input.Id)

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
