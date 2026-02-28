package application_game_test

import (
	"os"
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	game_doubles_test "retrolauncher/backend/src/app/games/internal/tests/doubles"
	"testing"
)

var sut application.CreateShortcut
var repository *game_doubles_test.MemoryGameRepository
var gameInstance *game.Game
var shortcutService *mockShortcutService

func TestMain(m *testing.M) {
	shortcutService = &mockShortcutService{}
	repository = &game_doubles_test.MemoryGameRepository{}
	factory := game_factories.NewDefaultGameFactory(game_doubles_test.NewMockFileSystem())
	gameInstance, _ = factory.CreateGame(
		"Test Game",
		platform.New(platform.TypeRetroArch, "/path/to/platform"),
		"/path/to/test/game",
		"/path/to/test/cover.jpg",
	)

	repository.Save(gameInstance)

	sut = application.NewCreateShortcut(repository, shortcutService)
	code := m.Run()
	os.Exit(code)
}

func Test_it_should_be_able_to_create_a_shortcut(t *testing.T) {
	err := sut.Execute(gameInstance.GetId().String())

	if err != nil {
		t.Error("Expected no error, but got", err)
		return
	}

	if shortcutService.shortcutGameId != gameInstance.GetId().String() {
		t.Error("Expected shortcut game id to be", gameInstance.GetId().String(), "but got", shortcutService.shortcutGameId)
		return
	}
}

func Test_it_should_not_be_able_to_create_a_shortcut_when_game_not_exists(t *testing.T) {
	err := sut.Execute("invalid-id")

	if err == nil {
		t.Error("Expected error due to invalid game id, but got none.")
		return
	}
}

type mockShortcutService struct {
	shortcutGameId   string
	shortcutGameName string
}

func (m *mockShortcutService) CreateDesktopShortcut(gameId, gameName, gameCover string) error {
	m.shortcutGameId = gameId
	m.shortcutGameName = gameName
	return nil
}

func (m *mockShortcutService) Clear() {
	m.shortcutGameId = ""
	m.shortcutGameName = ""
}
