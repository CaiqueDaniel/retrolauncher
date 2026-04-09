//go:build windows

package services

import (
	"os"
	"os/user"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"
	shared_app "retrolauncher/backend/src/shared/application"

	"github.com/jxeng/shortcut"
)

type JxengWindowsShortcutService struct {
	imageToIcoService    application.ImageToIcoService
	allowCreateOnDesktop bool
	lastShortcutCreate   *shortcut.Shortcut
}

func NewJxengWindowsShortcutService(
	imageToIcoService application.ImageToIcoService,
) application.ShortcutService {
	return &JxengWindowsShortcutService{
		imageToIcoService:    imageToIcoService,
		allowCreateOnDesktop: true,
	}
}

func NewJxengWindowsShortcutServiceForTesting(
	imageToIcoService application.ImageToIcoService,
) *JxengWindowsShortcutService {
	return &JxengWindowsShortcutService{
		imageToIcoService:    imageToIcoService,
		allowCreateOnDesktop: false,
	}
}

func (s *JxengWindowsShortcutService) CreateDesktopShortcut(gameId, gameName, gameCover string) error {
	binPath, err := s.getExecutablePath()

	if err != nil {
		return shared_app.InfrastructureError(err.Error())
	}

	iconPath, err := s.imageToIcoService.CreateIcoFrom(gameCover)

	if err != nil {
		return shared_app.InfrastructureError(err.Error())
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
			return shared_app.InfrastructureError(err.Error())
		}
	}

	s.lastShortcutCreate = shortcut

	return nil
}

func (s *JxengWindowsShortcutService) GetLastShortcutCreate() *shortcut.Shortcut {
	return s.lastShortcutCreate
}

func (s *JxengWindowsShortcutService) getExecutablePath() (string, error) {
	const startExecutableName = "start-game.exe"
	binPath, err := os.Executable()

	if err != nil {
		return "", err
	}

	return filepath.Join(filepath.Dir(binPath), startExecutableName), nil
}

func (s *JxengWindowsShortcutService) createShortcutOnDesktop(name string, shortcutConfig *shortcut.Shortcut) error {
	u, err := user.Current()

	if err != nil {
		return err
	}

	shortcutPath := filepath.Join(u.HomeDir, "Desktop", name+".lnk")
	shortcutConfig.ShortcutPath = shortcutPath
	return shortcut.Create(*shortcutConfig)
}
