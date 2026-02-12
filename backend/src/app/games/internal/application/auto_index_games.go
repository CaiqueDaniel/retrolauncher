package application

import (
	"errors"
	"fmt"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/domain"
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	"retrolauncher/backend/src/shared/application"
	"strings"
)

type AutoIndexGames interface {
	Execute() error
}

type useCase struct {
	repository            domain.GameRepository
	settingsService       SettingsService
	fs                    application.FileSystem
	platformsCoresService PlatformsCoresService
	gameFinderFactory     GameFinderFactory
	gameFactory           domain.GameFactory
}

func NewAutoIndexGames(
	repository domain.GameRepository,
	settingsService SettingsService,
	fs application.FileSystem,
	platformsCoresService PlatformsCoresService,
	gameFinderFactory GameFinderFactory,
	gameFactory domain.GameFactory,
) AutoIndexGames {
	return &useCase{
		repository:            repository,
		settingsService:       settingsService,
		fs:                    fs,
		platformsCoresService: platformsCoresService,
		gameFinderFactory:     gameFinderFactory,
		gameFactory:           gameFactory,
	}
}

func (uc *useCase) Execute() error {
	settings, err := uc.getSettings()

	if err != nil {
		return err
	}

	currentGamesFiles := uc.getCurrentGamesFiles()
	platformsCores, _ := uc.platformsCoresService.GetPlatformsCores()
	coresFiles := uc.getCoresFiles(settings)

	for platformExtension, coreSufix := range platformsCores {
		gameFinder := uc.gameFinderFactory.CreateFrom(platformExtension)
		gamesFiles := gameFinder.GetFilesFrom(settings.RomsFolderPath)

		uc.saveGames(uc.getNewGamesInstances(currentGamesFiles, gamesFiles, coresFiles, coreSufix))
	}

	return nil
}

func (uc *useCase) getSettings() (*Settings, error) {
	settings, err := uc.settingsService.GetSettings()

	if err != nil {
		return nil, err
	}

	if settings.RetroarchFolderPath == "" {
		return nil, errors.New("retroarch folder path is empty")
	}

	if settings.RomsFolderPath == "" {
		return nil, errors.New("roms folder path is empty")
	}

	return settings, nil
}

func (uc *useCase) getCurrentGamesFiles() map[string]bool {
	games := uc.repository.List(domain.ListGamesParams{})
	result := make(map[string]bool)

	for _, game := range games {
		result[game.GetPath()] = true
	}

	return result
}

func (uc *useCase) getCoresFiles(settings *Settings) []string {
	coresFiles, _ := uc.fs.ListFiles(filepath.Join(settings.RetroarchFolderPath, "cores"))
	return coresFiles
}

func (uc *useCase) getNewGamesInstances(
	currentGamesFiles map[string]bool,
	gamesFiles []string,
	coresFiles []string,
	coreSufix string,
) []*game.Game {
	result := make([]*game.Game, 0)

	for _, gameFile := range gamesFiles {
		if currentGamesFiles[gameFile] {
			continue
		}

		coreFile := uc.findCoreFile(coresFiles, coreSufix)

		if coreFile == "" {
			continue
		}

		game, err := uc.gameFactory.CreateGameFromPath(gameFile, platform.New(platform.TypeRetroArch, coreFile))

		if len(err) > 0 {
			continue
		}

		result = append(result, game)
	}

	return result
}

func (uc *useCase) findCoreFile(coresFiles []string, coreSufix string) string {
	for _, coreFile := range coresFiles {
		fmt.Println(coreFile)
		if strings.Contains(coreFile, coreSufix) {
			return coreFile
		}
	}

	return ""
}

func (uc *useCase) saveGames(games []*game.Game) {
	for _, game := range games {
		uc.repository.Save(game)
	}
}
