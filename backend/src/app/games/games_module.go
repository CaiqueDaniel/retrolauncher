package games

import (
	"net/http"
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/adapters"
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/delivery/desktop"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	"retrolauncher/backend/src/app/games/internal/persistance"
	"retrolauncher/backend/src/app/games/internal/services"
	"retrolauncher/backend/src/app/settings"
	shared_services "retrolauncher/backend/src/shared/services"
)

type GamesModule struct {
	GameController *desktop.GameController
}

func NewGamesModule() *GamesModule {
	fileSystem := shared_services.NewLocalFileSystem()
	gameFactory := game_factories.NewDefaultGameFactory(fileSystem)
	gameRepository := &persistance.StormGameRepository{}
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)
	gameFinderServiceFactory := services.NewGameFinderFactory()
	platformsCoresService := services.NewHardcodedPlatformsCoresService()
	settingsGateway := adapters.NewSettingsAdapter(settings.NewSettingsGateway())
	imageToIcoService := services.NewSergeymakinenImageToIcoService(fileSystem)
	shortcutService := services.NewShortcutService(imageToIcoService, os.Getenv("LANG"))
	binPath, _ := os.Executable()
	execPath := filepath.Dir(binPath)
	retroAchievementsGamesCache := services.NewLocalRetroAchievementsGamesCache(fileSystem, execPath)
	gamesAchievementsListCache := services.NewGamesAchievementsListCache()
	retroAchievementsGateway := services.NewHTTPRetroAchievementsGateway(
		http.DefaultClient,
		"https://retroachievements.org",
		retroAchievementsGamesCache,
	)

	return &GamesModule{
		GameController: desktop.New(
			application.NewCreateGame(gameFactory, gameRepository, imageUploader),
			application.NewUpdateGame(gameRepository, imageUploader),
			application.NewGetGame(gameRepository),
			application.NewListGames(gameRepository, imageUploader),
			application.NewGetPlatformTypes(),
			application.NewStartGame(gameRepository, services.NewStartGameService()),
			application.NewAutoIndexGames(
				gameRepository,
				settingsGateway,
				fileSystem,
				platformsCoresService,
				gameFinderServiceFactory,
				gameFactory,
			),
			application.NewCreateShortcut(gameRepository, shortcutService),
			application.NewListAchievementsFromGame(
				gameRepository,
				retroAchievementsGateway,
				settingsGateway,
			),
			gamesAchievementsListCache,
		),
	}
}
