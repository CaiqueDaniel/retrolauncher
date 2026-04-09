//go:build linux

package services_test

import (
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/services"
	"strings"
	"testing"
)

func Test_it_should_create_desktop_shortcut_on_linux(t *testing.T) {
	sut := services.NewNativeLinuxShortcutServiceForTesting()
	err := sut.CreateDesktopShortcut("game-id-123", "My Game", "/path/to/cover.png")

	if err != nil {
		t.Errorf("expected no error, got %v", err)
		return
	}

	content := sut.GetLastShortcutContent()
	if content == "" {
		t.Error("expected shortcut content to be set, got empty string")
		return
	}

	if !strings.Contains(content, "Name=My Game") {
		t.Errorf("expected shortcut content to contain game name, got:\n%s", content)
	}

	if !strings.Contains(content, "game-id-123") {
		t.Errorf("expected shortcut content to contain gameId as argument, got:\n%s", content)
	}

	if !strings.Contains(content, "Icon=/path/to/cover.png") {
		t.Errorf("expected shortcut content to contain cover path as icon, got:\n%s", content)
	}

	expectedBinPath, _ := os.Executable()
	expectedBinPath = filepath.Join(filepath.Dir(expectedBinPath), "start-game")
	if !strings.Contains(content, expectedBinPath) {
		t.Errorf("expected shortcut content to contain bin path %q, got:\n%s", expectedBinPath, content)
	}

	if !strings.Contains(content, "Type=Application") {
		t.Errorf("expected shortcut content to contain 'Type=Application', got:\n%s", content)
	}

	if !strings.Contains(content, "Categories=Game;") {
		t.Errorf("expected shortcut content to contain 'Categories=Game;', got:\n%s", content)
	}
}
