package services_test

import (
	"errors"
	"os"
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

func Test_it_should_create_desktop_shortcut(t *testing.T) {
	sut := services.NewJxengWindowsShortcutServiceForTesting(&StubSuccessImageToIcoService{})
	err := sut.CreateDesktopShortcut("gameId", "gameName", "gameCover")
	binPath, _ := os.Executable()

	if err != nil {
		t.Errorf("expected no error, got %v", err)
		return
	}

	if sut.GetLastShortcutCreate() == nil {
		t.Errorf("expected last shortcut create to be set, got nil")
		return
	}

	if sut.GetLastShortcutCreate().GameId != "gameId" {
		t.Errorf("expected last shortcut create gameId to be %v, got %v", "gameId", sut.GetLastShortcutCreate().GameId)
		return
	}

	if sut.GetLastShortcutCreate().GameName != "gameName" {
		t.Errorf("expected last shortcut create gameName to be %v, got %v", "gameName", sut.GetLastShortcutCreate().GameName)
		return
	}

	if sut.GetLastShortcutCreate().GameCover != "gameCover" {
		t.Errorf("expected last shortcut create gameCover to be %v, got %v", "gameCover", sut.GetLastShortcutCreate().GameCover)
		return
	}

	if sut.GetLastShortcutCreate().BinPath != binPath {
		t.Errorf("expected last shortcut create pointsToPath to be %v, got %v", binPath, sut.GetLastShortcutCreate().BinPath)
		return
	}
}

var stubError = errors.New("stub error")

func Test_it_should_escalate_error_from_ico_service(t *testing.T) {
	sut := services.NewJxengWindowsShortcutService(&StubErrorImageToIcoService{})
	err := sut.CreateDesktopShortcut("gameId", "gameName", "gameCover")

	if err == nil {
		t.Errorf("expected error, got nil")
	}

	if err != stubError {
		t.Errorf("expected error %v, got %v", stubError, err)
	}
}

type StubErrorImageToIcoService struct{}

func (s *StubErrorImageToIcoService) CreateIcoFrom(path string) (string, error) {
	return "", stubError
}

type StubSuccessImageToIcoService struct{}

func (s *StubSuccessImageToIcoService) CreateIcoFrom(path string) (string, error) {
	return "", nil
}
