//go:build windows

package services_test

import (
	"errors"
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/services"
	"retrolauncher/backend/src/shared/application"
	"testing"
)

func Test_it_should_create_desktop_shortcut(t *testing.T) {
	sut := services.NewJxengWindowsShortcutServiceForTesting(&StubSuccessImageToIcoService{})
	err := sut.CreateDesktopShortcut("gameId", "gameName", "gameCover")
	expectedBinPath, _ := os.Executable()
	expectedBinPath = filepath.Join(filepath.Dir(expectedBinPath), "start-game.exe")

	if err != nil {
		t.Errorf("expected no error, got %v", err)
		return
	}

	if sut.GetLastShortcutCreate() == nil {
		t.Errorf("expected last shortcut create to be set, got nil")
		return
	}

	if sut.GetLastShortcutCreate().Arguments != "gameId" {
		t.Errorf("expected last shortcut create gameId to be %v, got %v", "gameId", sut.GetLastShortcutCreate().Arguments)
		return
	}

	if sut.GetLastShortcutCreate().Description != "gameName" {
		t.Errorf("expected last shortcut create gameName to be %v, got %v", "gameName", sut.GetLastShortcutCreate().Description)
		return
	}

	if sut.GetLastShortcutCreate().IconLocation != "icon.ico" {
		t.Errorf("expected last shortcut create gameCover to be %v, got %v", "icon.ico", sut.GetLastShortcutCreate().IconLocation)
		return
	}

	if sut.GetLastShortcutCreate().Target != expectedBinPath {
		t.Errorf("expected last shortcut create pointsToPath to be %v, got %v", expectedBinPath, sut.GetLastShortcutCreate().Target)
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

	if err != application.InfrastructureError(stubError.Error()) {
		t.Errorf("expected error %v, got %v", application.InfrastructureError(stubError.Error()), err)
	}
}

type StubErrorImageToIcoService struct{}

func (s *StubErrorImageToIcoService) CreateIcoFrom(path string) (string, error) {
	return "", stubError
}

type StubSuccessImageToIcoService struct{}

func (s *StubSuccessImageToIcoService) CreateIcoFrom(path string) (string, error) {
	return "icon.ico", nil
}
