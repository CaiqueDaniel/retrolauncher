package services

import (
	"os"
	"os/user"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"

	"github.com/jxeng/shortcut"
)

type JxengWindowsShortcutService interface {
	application.ShortcutService
	GetLastShortcutCreate() *shortcut.Shortcut
}

type jxengWindowsShortcutService struct {
	imageToIcoService    application.ImageToIcoService
	allowCreateOnDesktop bool
	lastShortcutCreate   *shortcut.Shortcut
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
	binPath, err := s.getExecutablePath()

	if err != nil {
		return err
	}

	iconPath, err := s.imageToIcoService.CreateIcoFrom(gameCover)

	if err != nil {
		return err
	}

	shortcut := &shortcut.Shortcut{
		Target:           binPath,
		IconLocation:     iconPath,
		Arguments:        gameId,
		Description:      gameName,
		Hotkey:           "",
		WindowStyle:      "1",
		WorkingDirectory: "",
	}

	if s.allowCreateOnDesktop {
		err = s.createShortcutOnDesktop(gameName, shortcut)

		if err != nil {
			return err
		}
	}

	s.lastShortcutCreate = shortcut

	return nil
}

func (s *jxengWindowsShortcutService) GetLastShortcutCreate() *shortcut.Shortcut {
	return s.lastShortcutCreate
}

func (s *jxengWindowsShortcutService) getExecutablePath() (string, error) {
	const startExecutableName = "start-game.exe"
	binPath, err := os.Executable()

	if err != nil {
		return "", err
	}

	return filepath.Join(filepath.Dir(binPath), startExecutableName), nil
}

func (s *jxengWindowsShortcutService) createShortcutOnDesktop(name string, shortcutConfig *shortcut.Shortcut) error {
	u, err := user.Current()

	if err != nil {
		return err
	}

	shortcutPath := filepath.Join(u.HomeDir, "Desktop", name+".lnk")
	shortcutConfig.ShortcutPath = shortcutPath
	return shortcut.Create(*shortcutConfig)
}
