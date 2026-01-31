package games

import (
	"retrolauncher/backend/src/app/games/application"
	"retrolauncher/backend/src/app/games/delivery/desktop/game_controller"
	game_factories "retrolauncher/backend/src/app/games/factories"
	"retrolauncher/backend/src/app/games/persistance"
	"retrolauncher/backend/src/app/games/services/os_start_game_service"
	shared_services "retrolauncher/backend/src/shared/services"
)

type GamesModule struct {
	GameController *game_controller.GameController
}

func NewGamesModule() *GamesModule {
	gameFactory := &game_factories.DefaultGameFactory{}
	gameRepository := &persistance.StormGameRepository{}
	fileSystem := &shared_services.LocalFileSystem{}
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)

	return &GamesModule{
		GameController: game_controller.New(
			application.NewCreateGame(gameFactory, gameRepository, imageUploader),
			application.NewUpdateGame(gameRepository, imageUploader),
			application.NewGetGame(gameRepository),
			application.NewListGames(gameRepository, imageUploader),
			application.NewGetPlatformTypes(),
			application.NewStartGame(gameRepository, os_start_game_service.New()),
		),
	}
}
