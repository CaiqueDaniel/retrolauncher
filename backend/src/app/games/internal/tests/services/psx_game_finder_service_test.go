package services_test

import (
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

func Test_PSXGameFinderService_GetFilesFrom(t *testing.T) {
	tempDir := t.TempDir()
	service := services.NewPSXGameFinderService()

	// 1. CUE pointing to Valid BIN
	validCue := "game_valid.cue"
	validBin := "game_valid.bin"
	createFileWithContent(t, tempDir, validCue, `FILE "game_valid.bin" BINARY`)
	createFileWithContent(t, tempDir, validBin, "Sony Computer Entertainment")

	// 2. CUE pointing to Missing BIN
	missingBinCue := "game_missing_bin.cue"
	createFileWithContent(t, tempDir, missingBinCue, `FILE "game_missing.bin" BINARY`)

	// 3. CUE pointing to Invalid BIN (wrong magic string)
	invalidBinCue := "game_invalid_bin.cue"
	invalidBin := "game_invalid.bin"
	createFileWithContent(t, tempDir, invalidBinCue, `FILE "game_invalid.bin" BINARY`)
	createFileWithContent(t, tempDir, invalidBin, "Sega Enterprises")

	// 4. Not a CUE file
	notCue := "game.txt"
	createFileWithContent(t, tempDir, notCue, "content")

	// 5. CUE with invalid format
	invalidFormatCue := "game_bad_format.cue"
	createFileWithContent(t, tempDir, invalidFormatCue, `INVALID CONTENT`)

	files := service.GetFilesFrom(tempDir)

	if len(files) != 1 {
		t.Errorf("Expected 1 valid PSX game, got %d", len(files))
	}

	expectedPath := filepath.Join(tempDir, validCue)
	if len(files) > 0 && files[0] != expectedPath {
		t.Errorf("Expected file %s, got %s", expectedPath, files[0])
	}
}

func createFileWithContent(t *testing.T, dir, name, content string) {
	path := filepath.Join(dir, name)
	if err := os.WriteFile(path, []byte(content), 0644); err != nil {
		t.Fatalf("Failed to create file %s: %v", path, err)
	}
}
