package cli

import "retrolauncher/backend/src/app/games/internal/application"

type StartGameCommand interface {
	Execute(id string)
}

type startGameCommand struct {
	startGame application.StartGame
}

func NewStartGameCommand(startGame application.StartGame) StartGameCommand {
	return &startGameCommand{
		startGame: startGame,
	}
}

func (s *startGameCommand) Execute(id string) {
	s.startGame.Execute(application.StartGameInput{
		GameId: id,
	})
}
