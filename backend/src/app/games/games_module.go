package games

import (
	game_app "retrolauncher/backend/src/app/games/application"
	"retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/get_game"
	"retrolauncher/backend/src/app/games/application/get_platform_types"
	"retrolauncher/backend/src/app/games/application/start_game"
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
			create_game.New(gameFactory, gameRepository, imageUploader),
			game_app.NewUpdateGame(gameRepository, imageUploader),
			get_game.New(gameRepository),
			game_app.NewListGames(gameRepository, imageUploader),
			get_platform_types.New(),
			start_game.New(gameRepository, os_start_game_service.New()),
		),
	}
}
