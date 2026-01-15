package get_game

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
)

type GetGame struct {
	Execute func(input Input) (*Output, error)
}

func New(repository domain.GameRepository) *GetGame {
	return &GetGame{
		Execute: func(input Input) (*Output, error) { return execute(input, repository) },
	}
}

func execute(input Input, repository domain.GameRepository) (*Output, error) {
	game, err := repository.Get(input.Id)

	if err != nil {
		return nil, err
	}

	if game == nil {
		return nil, errors.New("game not found")
	}

	return &Output{
		Id:       game.GetId().String(),
		Name:     game.GetName(),
		Platform: game.GetPlatformType().GetPlatformType(),
		Cover:    game.GetCover(),
		Path:     game.GetPath(),
	}, nil
}

type Input struct {
	Id string
}

type Output struct {
	Id       string
	Name     string
	Platform string
	Cover    string
	Path     string
}
