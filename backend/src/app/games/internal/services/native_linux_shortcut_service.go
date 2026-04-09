//go:build linux

package services

import (
	"fmt"
	"os"
	"os/user"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"
	shared_app "retrolauncher/backend/src/shared/application"
)

const desktopFileTemplate = `[Desktop Entry]
Version=1.0
Type=Application
Name=%s
Exec=%s %s
Icon=%s
Comment=%s
Terminal=false
Categories=Game;
`

type NativeLinuxShortcutService struct {
	allowCreateOnDesktop bool
	lastShortcutPath     string
	lastShortcutContent  string
}

func NewNativeLinuxShortcutService() application.ShortcutService {
	return &NativeLinuxShortcutService{allowCreateOnDesktop: true}
}

func NewNativeLinuxShortcutServiceForTesting() *NativeLinuxShortcutService {
	return &NativeLinuxShortcutService{allowCreateOnDesktop: false}
}

func (s *NativeLinuxShortcutService) CreateDesktopShortcut(gameId, gameName, gameCover string) error {
	binPath, err := s.getExecutablePath()
	if err != nil {
		return shared_app.InfrastructureError(err.Error())
	}

	content := fmt.Sprintf(desktopFileTemplate, gameName, binPath, gameId, gameCover, gameName)
	s.lastShortcutContent = content

	if s.allowCreateOnDesktop {
		desktopDir, err := s.getDesktopDir()
		if err != nil {
			return shared_app.InfrastructureError(err.Error())
		}

		shortcutPath := filepath.Join(desktopDir, gameName+".desktop")
		s.lastShortcutPath = shortcutPath

		if err := os.WriteFile(shortcutPath, []byte(content), 0755); err != nil {
			return shared_app.InfrastructureError(err.Error())
		}
	}

	return nil
}

func (s *NativeLinuxShortcutService) GetLastShortcutContent() string {
	return s.lastShortcutContent
}

func (s *NativeLinuxShortcutService) getExecutablePath() (string, error) {
	const startExecutableName = "start-game"
	binPath, err := os.Executable()
	if err != nil {
		return "", err
	}
	return filepath.Join(filepath.Dir(binPath), startExecutableName), nil
}

func (s *NativeLinuxShortcutService) getDesktopDir() (string, error) {
	// Respeita XDG_DESKTOP_DIR se definido
	if xdgDesktop := os.Getenv("XDG_DESKTOP_DIR"); xdgDesktop != "" {
		return xdgDesktop, nil
	}

	u, err := user.Current()
	if err != nil {
		return "", err
	}
	return filepath.Join(u.HomeDir, "Desktop"), nil
}
