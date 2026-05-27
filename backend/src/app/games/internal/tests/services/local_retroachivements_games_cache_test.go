package services_test

import (
	"encoding/json"
	"errors"
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

const cacheFolder = "resources/retroachievements"

// stubFileSystem is a local test double that correctly simulates
// directory listing (returning only filenames, not full paths).
type stubFileSystem struct {
	// dirFiles maps a directory path to a list of file names within it.
	dirFiles map[string][]string
	// fileContents maps a full file path to its byte content.
	fileContents map[string][]byte
	// listFilesErr is an optional error to return from ListFiles.
	listFilesErr error
}

func newStubFileSystem() *stubFileSystem {
	return &stubFileSystem{
		dirFiles:     make(map[string][]string),
		fileContents: make(map[string][]byte),
	}
}

func (s *stubFileSystem) addFile(dir, name string, content []byte) {
	s.dirFiles[dir] = append(s.dirFiles[dir], cacheFolder+"/"+name)
	s.fileContents[dir+"/"+name] = content
}

func (s *stubFileSystem) ListFiles(path string) ([]string, error) {
	if s.listFilesErr != nil {
		return nil, s.listFilesErr
	}
	return s.dirFiles[path], nil
}

func (s *stubFileSystem) ReadFile(path string) ([]byte, error) {
	data, ok := s.fileContents[path]
	if !ok {
		return nil, errors.New("file not found: " + path)
	}
	return data, nil
}

func (s *stubFileSystem) SaveFile(path string, data []byte) error    { return nil }
func (s *stubFileSystem) ExistsFile(path string) bool                { return false }
func (s *stubFileSystem) CopyFile(src string, dst string) error      { return nil }
func (s *stubFileSystem) RemoveFile(path string) error               { return nil }
func (s *stubFileSystem) GetFileName(path string) string             { return path }
func (s *stubFileSystem) GetFileExtension(path string) string        { return "" }
func (s *stubFileSystem) GetFileMD5Hash(path string) (string, error) { return "", nil }

// helpers

type cacheEntry struct {
	ID     int      `json:"ID"`
	Hashes []string `json:"Hashes"`
}

func marshalEntries(t *testing.T, entries []cacheEntry) []byte {
	t.Helper()
	data, err := json.Marshal(entries)
	if err != nil {
		t.Fatalf("failed to marshal cache entries: %v", err)
	}
	return data
}

// -------------------------------------------------------------------
// Tests
// -------------------------------------------------------------------

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_ReturnsEmptyMap_WhenNoFilesExist(t *testing.T) {
	fs := newStubFileSystem()
	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	result := sut.GetCachedGameRelation()

	if len(result) != 0 {
		t.Errorf("expected empty map, got %d entries", len(result))
	}
}

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_ReturnsEmptyMap_WhenListFilesErrors(t *testing.T) {
	fs := newStubFileSystem()
	fs.listFilesErr = errors.New("permission denied")
	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	result := sut.GetCachedGameRelation()

	if len(result) != 0 {
		t.Errorf("expected empty map on ListFiles error, got %d entries", len(result))
	}
}

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_ParsesSingleJsonFile(t *testing.T) {
	fs := newStubFileSystem()
	fs.addFile(cacheFolder, "nes.json", marshalEntries(t, []cacheEntry{
		{ID: 1, Hashes: []string{"abc123", "def456"}},
		{ID: 2, Hashes: []string{"ghi789"}},
	}))

	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	result := sut.GetCachedGameRelation()

	expected := map[string]string{
		"abc123": "1",
		"def456": "1",
		"ghi789": "2",
	}

	if len(result) != len(expected) {
		t.Errorf("expected %d relations, got %d", len(expected), len(result))
	}

	for hash, gameID := range expected {
		if result[hash] != gameID {
			t.Errorf("expected hash %q → game ID %q, got %q", hash, gameID, result[hash])
		}
	}
}

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_ParsesMultipleJsonFiles(t *testing.T) {
	fs := newStubFileSystem()
	fs.addFile(cacheFolder, "nes.json", marshalEntries(t, []cacheEntry{
		{ID: 10, Hashes: []string{"nes_hash_1"}},
	}))
	fs.addFile(cacheFolder, "snes.json", marshalEntries(t, []cacheEntry{
		{ID: 20, Hashes: []string{"snes_hash_1", "snes_hash_2"}},
	}))

	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	result := sut.GetCachedGameRelation()

	if result["nes_hash_1"] != "10" {
		t.Errorf("expected nes_hash_1 → 10, got %q", result["nes_hash_1"])
	}
	if result["snes_hash_1"] != "20" {
		t.Errorf("expected snes_hash_1 → 20, got %q", result["snes_hash_1"])
	}
	if result["snes_hash_2"] != "20" {
		t.Errorf("expected snes_hash_2 → 20, got %q", result["snes_hash_2"])
	}
}

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_SkipsNonJsonFiles(t *testing.T) {
	fs := newStubFileSystem()
	fs.addFile(cacheFolder, "readme.txt", []byte("this is not json"))
	fs.addFile(cacheFolder, "games.json", marshalEntries(t, []cacheEntry{
		{ID: 5, Hashes: []string{"valid_hash"}},
	}))

	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	result := sut.GetCachedGameRelation()

	if len(result) != 1 {
		t.Errorf("expected 1 relation (non-JSON file skipped), got %d", len(result))
	}
	if result["valid_hash"] != "5" {
		t.Errorf("expected valid_hash → 5, got %q", result["valid_hash"])
	}
}

func Test_LocalRetroAchievementsGamesCache_GetCachedGameRelation_DoesNotReloadFilesOnSubsequentCalls(t *testing.T) {
	fs := newStubFileSystem()
	fs.addFile(cacheFolder, "games.json", marshalEntries(t, []cacheEntry{
		{ID: 99, Hashes: []string{"hash_a"}},
	}))

	sut := services.NewLocalRetroAchievementsGamesCache(fs, "")

	// First call loads from files.
	first := sut.GetCachedGameRelation()

	// Add a new file after first call; it must NOT appear in the second result
	// because data should be served from the in-memory cache.
	fs.addFile(cacheFolder, "extra.json", marshalEntries(t, []cacheEntry{
		{ID: 100, Hashes: []string{"hash_b"}},
	}))

	second := sut.GetCachedGameRelation()

	if len(first) != len(second) {
		t.Errorf("expected same cached result on second call: first=%d, second=%d", len(first), len(second))
	}

	if _, found := second["hash_b"]; found {
		t.Error("expected cache NOT to reload from disk on second call, but new data was picked up")
	}
}
