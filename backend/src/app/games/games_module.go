package games

import (
	"retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/get_game"
	"retrolauncher/backend/src/app/games/application/get_platform_types"
	"retrolauncher/backend/src/app/games/application/list_games"
	"retrolauncher/backend/src/app/games/application/start_game"
	"retrolauncher/backend/src/app/games/application/update_game"
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
			update_game.New(gameRepository),
			get_game.New(gameRepository),
			list_games.New(gameRepository),
			get_platform_types.New(),
			start_game.New(gameRepository, os_start_game_service.New()),
		),
	}
}
