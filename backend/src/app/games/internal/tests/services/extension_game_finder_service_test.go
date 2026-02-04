package services_test

import (
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

func Test_ExtensionGameFinderService_GetFilesFrom(t *testing.T) {
	tempDir := t.TempDir()

	// Create dummy files
	createFile(t, tempDir, "game1.nes")
	createFile(t, tempDir, "game2.sfc")
	createFile(t, tempDir, "game3.gb")
	createFile(t, tempDir, "game4.txt")
	createDir(t, tempDir, "subdir.nes")

	tests := []struct {
		name          string
		finderCreator func() interface {
			GetFilesFrom(string) []string
		}
		expectedCount int
	}{
		{
			name:          "NES Finder",
			finderCreator: func() interface{ GetFilesFrom(string) []string } { return services.NewNESGameFinderService() },
			expectedCount: 1,
		},
		{
			name:          "SNES Finder",
			finderCreator: func() interface{ GetFilesFrom(string) []string } { return services.NewSNESGameFinderService() },
			expectedCount: 1,
		},
		{
			name:          "GB Finder",
			finderCreator: func() interface{ GetFilesFrom(string) []string } { return services.NewGBGameFinderService() },
			expectedCount: 1,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			finder := tt.finderCreator()
			files := finder.GetFilesFrom(tempDir)

			if len(files) != tt.expectedCount {
				t.Errorf("%s found %d files, expected %d", tt.name, len(files), tt.expectedCount)
			}
		})
	}
}

func createFile(t *testing.T, dir, name string) {
	path := filepath.Join(dir, name)
	if err := os.WriteFile(path, []byte(""), 0644); err != nil {
		t.Fatalf("Failed to create file %s: %v", path, err)
	}
}

func createDir(t *testing.T, dir, name string) {
	path := filepath.Join(dir, name)
	if err := os.Mkdir(path, 0755); err != nil {
		t.Fatalf("Failed to create dir %s: %v", path, err)
	}
}
