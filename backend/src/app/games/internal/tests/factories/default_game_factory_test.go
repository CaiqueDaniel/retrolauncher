package game_factories_test

import (
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	"testing"
)

func Test_it_should_be_able_to_create_a_game(t *testing.T) {
	factory := &game_factories.DefaultGameFactory{}
	game, _ := factory.CreateGame("Test Game", platform.New(platform.TypeRetroArch, "/path"), "/path/to/test/game", "/path/to/test/cover.jpg")

	if game == nil {
		t.Error("Expected game to be created, but it is nil.")
		return
	}

	if game.GetName() != "Test Game" {
		t.Errorf("Expected game name to be 'Test Game', but got '%s'", game.GetName())
		return
	}

	if game.GetPlatformType().GetPlatformType() != "RetroArch" {
		t.Errorf("Expected game platform to be 'RetroArch', but got '%s'", game.GetPlatformType().GetPlatformType())
		return
	}

	if game.GetPlatformType().GetPath() != "/path" {
		t.Errorf("Expected platform path to be '/path', but got '%s'", game.GetPlatformType().GetPath())
		return
	}

	if game.GetPath() != "/path/to/test/game" {
		t.Errorf("Expected game path to be '/path/to/test/game', but got '%s'", game.GetPath())
		return
	}

	if game.GetCover() != "/path/to/test/cover.jpg" {
		t.Errorf("Expected game cover to be '/path/to/test/cover.jpg', but got '%s'", game.GetCover())
		return
	}
}
