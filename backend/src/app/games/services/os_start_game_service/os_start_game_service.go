package os_start_game_service

import (
	"os/exec"
	"path/filepath"
)

type OSStartGameService struct{}

func New() *OSStartGameService {
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
