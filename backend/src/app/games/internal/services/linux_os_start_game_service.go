//go:build linux

package services

import (
	"os/exec"
	"retrolauncher/backend/src/app/games/internal/application"
)

type OSStartGameService struct{}

func NewStartGameService() application.StartGameService {
	return NewLinuxOSStartGameService()
}

func NewLinuxOSStartGameService() application.StartGameService {
	return &OSStartGameService{}
}

func (s *OSStartGameService) StartGame(gamePath, platformPath string) error {
	command := exec.Command("retroarch", "-L", platformPath, gamePath)
	err := command.Start()

	if err != nil {
		return err
	}

	command.Wait()
	return nil
}
