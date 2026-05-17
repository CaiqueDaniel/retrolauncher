package games

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/delivery/cli"
	"retrolauncher/backend/src/app/games/internal/persistance"
	"retrolauncher/backend/src/app/games/internal/services"
)

type GamesCLIModule struct {
	StartGameCommand cli.StartGameCommand
}

func NewGamesCLIModule() *GamesCLIModule {
	gameRepository := &persistance.StormGameRepository{}
	startGameUseCase := application.NewStartGame(gameRepository, services.NewStartGameService())

	return &GamesCLIModule{
		StartGameCommand: cli.NewStartGameCommand(startGameUseCase),
	}
}
