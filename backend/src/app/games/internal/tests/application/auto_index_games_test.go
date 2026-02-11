package application_game_test

import (
	"errors"
	"retrolauncher/backend/src/app/games/internal/application"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	game_doubles_test "retrolauncher/backend/src/app/games/internal/tests/doubles"
	"testing"
)

// --- Mocks ---

type mockSettingsService struct {
	settings *application.Settings
	err      error
}

func (m *mockSettingsService) GetSettings() (*application.Settings, error) {
	return m.settings, m.err
}

type mockPlatformsCoresService struct {
	cores map[string]string
	err   error
}

func (m *mockPlatformsCoresService) GetPlatformsCores() (map[string]string, error) {
	return m.cores, m.err
}

type mockGameFinderService struct {
	files []string
}

func (m *mockGameFinderService) GetFilesFrom(folder string) []string {
	return m.files
}

type mockGameFinderFactory struct {
	finders map[string]*mockGameFinderService
}

func (m *mockGameFinderFactory) CreateFrom(platformName string) application.GameFinderService {
	if finder, ok := m.finders[platformName]; ok {
		return finder
	}
	return &mockGameFinderService{}
}

// --- Tests ---

func Test_it_should_index_new_games(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	fileSystem.SaveFile("/retroarch/cores/snes9x_libretro.dll", []byte("core"))

	settingsService := &mockSettingsService{
		settings: &application.Settings{
			RetroarchFolderPath: "/retroarch",
			RomsFolderPath:      "/roms",
		},
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{
			".sfc": "snes9x",
		},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{
			".sfc": {files: []string{"/roms/game1.sfc", "/roms/game2.sfc"}},
		},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() != 2 {
		t.Errorf("Expected 2 games to be indexed, but got %d", repository.Size())
	}
}

func Test_it_should_skip_already_indexed_games(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	fileSystem.SaveFile("/retroarch/cores/snes9x_libretro.dll", []byte("core"))

	settingsService := &mockSettingsService{
		settings: &application.Settings{
			RetroarchFolderPath: "/retroarch",
			RomsFolderPath:      "/roms",
		},
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{
			".sfc": "snes9x",
		},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{
			".sfc": {files: []string{"/roms/game1.sfc", "/roms/game2.sfc"}},
		},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	// First execution indexes the games
	sut.Execute()

	if repository.Size() != 2 {
		t.Errorf("Expected 2 games after first execution, but got %d", repository.Size())
		return
	}

	// Second execution should not duplicate games
	sut.Execute()

	if repository.Size() != 2 {
		t.Errorf("Expected still 2 games after second execution, but got %d", repository.Size())
	}
}

func Test_it_should_return_error_when_settings_service_fails(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	settingsService := &mockSettingsService{
		err: errors.New("settings not found"),
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	err := sut.Execute()

	if err == nil {
		t.Error("Expected error when settings service fails, but got none")
		return
	}

	if repository.Size() != 0 {
		t.Errorf("Expected no games to be indexed, but got %d", repository.Size())
	}
}

func Test_it_should_skip_games_when_core_file_is_not_found(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	// No core files added to the file system

	settingsService := &mockSettingsService{
		settings: &application.Settings{
			RetroarchFolderPath: "/retroarch",
			RomsFolderPath:      "/roms",
		},
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{
			".sfc": "snes9x",
		},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{
			".sfc": {files: []string{"/roms/game1.sfc"}},
		},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() != 0 {
		t.Errorf("Expected no games when core is missing, but got %d", repository.Size())
	}
}

func Test_it_should_not_index_when_no_games_found(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	fileSystem.SaveFile("/retroarch/cores/snes9x_libretro.dll", []byte("core"))

	settingsService := &mockSettingsService{
		settings: &application.Settings{
			RetroarchFolderPath: "/retroarch",
			RomsFolderPath:      "/roms",
		},
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{
			".sfc": "snes9x",
		},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{
			".sfc": {files: []string{}},
		},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() != 0 {
		t.Errorf("Expected no games to be indexed, but got %d", repository.Size())
	}
}

func Test_it_should_index_games_from_multiple_platforms(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	factory := game_factories.NewDefaultGameFactory(fileSystem)

	fileSystem.SaveFile("/retroarch/cores/snes9x_libretro.dll", []byte("core"))
	fileSystem.SaveFile("/retroarch/cores/genesis_plus_gx_libretro.dll", []byte("core"))

	settingsService := &mockSettingsService{
		settings: &application.Settings{
			RetroarchFolderPath: "/retroarch",
			RomsFolderPath:      "/roms",
		},
	}

	platformsCoresService := &mockPlatformsCoresService{
		cores: map[string]string{
			".sfc": "snes9x",
			".md":  "genesis_plus_gx",
		},
	}

	gameFinderFactory := &mockGameFinderFactory{
		finders: map[string]*mockGameFinderService{
			".sfc": {files: []string{"/roms/snes_game.sfc"}},
			".md":  {files: []string{"/roms/mega_drive_game.md"}},
		},
	}

	sut := application.NewAutoIndexGames(
		repository,
		settingsService,
		fileSystem,
		platformsCoresService,
		gameFinderFactory,
		factory,
	)

	err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() != 2 {
		t.Errorf("Expected 2 games from 2 platforms, but got %d", repository.Size())
	}
}
