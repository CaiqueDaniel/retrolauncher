package game_factories_test

import (
	game_factories "retrolauncher/backend/src/app/games/factories"
	"testing"
)

func Test_it_should_be_able_to_create_a_game(t *testing.T) {
	factory := &game_factories.DefaultGameFactory{}
	game := factory.CreateGame("Test Game", "Test Platform", "/path/to/test/game", "/path/to/test/cover.jpg")

	if game == nil {
		t.Error("Expected game to be created, but it is nil.")
		return
	}

	if game.GetName() != "Test Game" {
		t.Errorf("Expected game name to be 'Test Game', but got '%s'", game.GetName())
		return
	}

	if game.GetPlatform() != "Test Platform" {
		t.Errorf("Expected game platform to be 'Test Platform', but got '%s'", game.GetPlatform())
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
