package games

import (
	"retrolauncher/backend/src/app/games/internal/adapters"
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/delivery/desktop/game_controller"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	"retrolauncher/backend/src/app/games/internal/persistance"
	"retrolauncher/backend/src/app/games/internal/services"
	"retrolauncher/backend/src/app/games/internal/services/os_start_game_service"
	"retrolauncher/backend/src/app/settings"
	shared_services "retrolauncher/backend/src/shared/services"
)

type GamesModule struct {
	GameController *game_controller.GameController
}

func NewGamesModule() *GamesModule {
	fileSystem := shared_services.NewLocalFileSystem()
	gameFactory := game_factories.NewDefaultGameFactory(fileSystem)
	gameRepository := &persistance.StormGameRepository{}
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)
	gameFinderServiceFactory := services.NewGameFinderFactory()
	platformsCoresService := services.NewHardcodedPlatformsCoresService()
	settingsGateway := adapters.NewSettingsAdapter(settings.NewSettingsGateway())

	return &GamesModule{
		GameController: game_controller.New(
			application.NewCreateGame(gameFactory, gameRepository, imageUploader),
			application.NewUpdateGame(gameRepository, imageUploader),
			application.NewGetGame(gameRepository),
			application.NewListGames(gameRepository, imageUploader),
			application.NewGetPlatformTypes(),
			application.NewStartGame(gameRepository, os_start_game_service.New()),
			application.NewAutoIndexGames(
				gameRepository,
				settingsGateway,
				fileSystem,
				platformsCoresService,
				gameFinderServiceFactory,
				gameFactory,
			),
		),
	}
}
