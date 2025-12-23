package application_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

func GetGame(input GetGameInput, repository game.GameRepository) (*game.Game, error) {
	game, err := repository.Get(input.Id)

	if err != nil {
		return nil, err
	}

	if game == nil {
		return nil, errors.New("game not found")
	}

	return game, nil
}

type GetGameInput struct {
	Id string
}
