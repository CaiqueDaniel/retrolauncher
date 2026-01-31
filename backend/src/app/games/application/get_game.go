package application

import (
	"errors"
	"retrolauncher/backend/src/app/games/domain"
)

type GetGame interface {
	Execute(input GetGameInput) (*GetGameOutput, error)
}

type getGame struct {
	repository domain.GameRepository
}

func NewGetGame(repository domain.GameRepository) GetGame {
	return &getGame{
		repository: repository,
	}
}

func (g *getGame) Execute(input GetGameInput) (*GetGameOutput, error) {
	game, err := g.repository.Get(input.Id)

	if err != nil {
		return nil, err
	}

	if game == nil {
		return nil, errors.New("game not found")
	}

	return &GetGameOutput{
		Id:           game.GetId().String(),
		Name:         game.GetName(),
		PlatformType: game.GetPlatformType().GetPlatformType(),
		PlatformPath: game.GetPlatformType().GetPath(),
		Cover:        game.GetCover(),
		Path:         game.GetPath(),
	}, nil
}

type GetGameInput struct {
	Id string
}

type GetGameOutput struct {
	Id           string
	Name         string
	PlatformType string
	PlatformPath string
	Cover        string
	Path         string
}
