package services

import (
	"os"
	"retrolauncher/backend/src/app/games/internal/application"

	"github.com/jxeng/shortcut"
)

type JxengWindowsShortcutService interface {
	application.ShortcutService
	GetLastShortcutCreate() *lastShortcutCreate
}

type jxengWindowsShortcutService struct {
	imageToIcoService    application.ImageToIcoService
	allowCreateOnDesktop bool
	lastShortcutCreate   *lastShortcutCreate
}

type lastShortcutCreate struct {
	GameId    string
	GameName  string
	GameCover string
	BinPath   string
}

func NewJxengWindowsShortcutService(
	imageToIcoService application.ImageToIcoService,
) JxengWindowsShortcutService {
	return &jxengWindowsShortcutService{
		imageToIcoService:    imageToIcoService,
		allowCreateOnDesktop: true,
	}
}

func NewJxengWindowsShortcutServiceForTesting(
	imageToIcoService application.ImageToIcoService,
) JxengWindowsShortcutService {
	return &jxengWindowsShortcutService{
		imageToIcoService:    imageToIcoService,
		allowCreateOnDesktop: false,
	}
}

func (s *jxengWindowsShortcutService) CreateDesktopShortcut(gameId, gameName, gameCover string) error {
	binPath, err := os.Executable()

	if err != nil {
		return err
	}

	iconPath, err := s.imageToIcoService.CreateIcoFrom(gameCover)

	if err != nil {
		return err
	}

	if s.allowCreateOnDesktop {
		err = shortcut.CreateDesktopShortcut(gameName, binPath, iconPath)

		if err != nil {
			return err
		}
	}

	s.lastShortcutCreate = &lastShortcutCreate{
		GameId:    gameId,
		GameName:  gameName,
		GameCover: gameCover,
		BinPath:   binPath,
	}

	return nil
}

func (s *jxengWindowsShortcutService) GetLastShortcutCreate() *lastShortcutCreate {
	return s.lastShortcutCreate
}
