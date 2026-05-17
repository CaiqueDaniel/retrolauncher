//go:build windows

package services

import (
	"os/exec"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"
)

type OSStartGameService struct{}

func NewStartGameService() application.StartGameService {
	return NewWindowsOSStartGameService()
}

func NewWindowsOSStartGameService() application.StartGameService {
	return &OSStartGameService{}
}

func (s *OSStartGameService) StartGame(gamePath, platformPath string) error {
	retroarchPath := filepath.Join(filepath.Dir(filepath.Dir(platformPath)), "retroarch.exe")
	command := exec.Command(retroarchPath, "-L", platformPath, gamePath)
	err := command.Start()

	if err != nil {
		return err
	}

	command.Wait()
	return nil
}
