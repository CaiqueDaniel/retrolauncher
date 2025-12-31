package create_game

import (
	"errors"
	"retrolauncher/backend/internal/app/games/domain/game"
)

type CreateGame struct {
	factory    game.GameFactory
	repository game.GameRepository
}

func New(factory game.GameFactory, repository game.GameRepository) *CreateGame {
	return &CreateGame{
		factory:    factory,
		repository: repository,
	}
}

func (cg *CreateGame) Execute(input Input) error {
	var err error

	game := cg.factory.CreateGame(input.Name, input.Platform, input.Path, input.Cover)

	if game == nil {
		return errors.New("failed to create game")
	}

	err = cg.repository.Save(game)
	return err
}

type Input struct {
	Name     string
	Platform string
	Path     string
	Cover    string
}
