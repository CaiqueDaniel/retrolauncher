//go:build linux

package services

import (
	"fmt"
	"os"
	"os/user"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"
	shared_app "retrolauncher/backend/src/shared/application"
	"strings"
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
	currentUserLanguage  string
}

func NewNativeLinuxShortcutService(currentUserLanguage string) application.ShortcutService {
	return &NativeLinuxShortcutService{allowCreateOnDesktop: true, currentUserLanguage: currentUserLanguage}
}

func NewNativeLinuxShortcutServiceForTesting(currentUserLanguage string) *NativeLinuxShortcutService {
	return &NativeLinuxShortcutService{allowCreateOnDesktop: false, currentUserLanguage: currentUserLanguage}
}

func NewShortcutService(imageToIcoService application.ImageToIcoService, currentUserLanguage string) application.ShortcutService {
	return NewNativeLinuxShortcutService(currentUserLanguage)
}

func (s *NativeLinuxShortcutService) CreateDesktopShortcut(gameId, gameName, gameCover string) error {
	binPath, err := s.getExecutablePath()

	if err != nil {
		return shared_app.InfrastructureError(err.Error())
	}

	desktopDir, err := s.getDesktopDir()

	if err != nil {
		return shared_app.InfrastructureError(err.Error())
	}

	content := fmt.Sprintf(desktopFileTemplate, gameName, binPath, gameId, gameCover, gameName)
	shortcutPath := filepath.Join(desktopDir, gameName+".desktop")

	s.lastShortcutPath = shortcutPath
	s.lastShortcutContent = content

	if s.allowCreateOnDesktop {
		if err := os.WriteFile(shortcutPath, []byte(content), 0755); err != nil {
			return shared_app.InfrastructureError(err.Error())
		}
	}

	return nil
}

func (s *NativeLinuxShortcutService) GetLastShortcutContent() string {
	return s.lastShortcutContent
}

func (s *NativeLinuxShortcutService) GetLastShortcutPath() string {
	return s.lastShortcutPath
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
	const baseDesktopDir = "Desktop"
	const ptBrDesktopDir = "Área de trabalho"
	const ptBrLanguageDefinition = "pt_BR"

	u, err := user.Current()

	if err != nil {
		return "", err
	}

	if strings.Contains(s.currentUserLanguage, ptBrLanguageDefinition) {
		return filepath.Join(u.HomeDir, ptBrDesktopDir), nil
	}

	return filepath.Join(u.HomeDir, baseDesktopDir), nil
}
