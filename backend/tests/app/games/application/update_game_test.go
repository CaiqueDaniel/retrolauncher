package application_game_test

import (
	application_game "retrolauncher/backend/internal/app/games/application"
	game_factories "retrolauncher/backend/internal/app/games/factories"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_update_a_game(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	factory := &game_factories.DefaultGameFactory{}
	game := factory.CreateGame("test", "test", "test", "test")
	repository.Save(game)

	err := application_game.UpdateGame(application_game.UpdateGameInput{
		ID:       game.GetId().String(),
		Name:     "Updated Test Game",
		Platform: "Updated Test Platform",
		Path:     "/path/to/updated/test/game",
		Cover:    "/path/to/updated/test/cover.jpg",
	}, repository)

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	updatedGame, err := repository.Get(game.GetId().String())
	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if updatedGame.GetName() != "Updated Test Game" {
		t.Errorf("Expected name to be 'Updated Test Game', but got: %s", updatedGame.GetName())
		return
	}

	if updatedGame.GetPlatform() != "Updated Test Platform" {
		t.Errorf("Expected platform to be 'Updated Test Platform', but got: %s", updatedGame.GetPlatform())
		return
	}

	if updatedGame.GetPath() != "/path/to/updated/test/game" {
		t.Errorf("Expected path to be '/path/to/updated/test/game', but got: %s", updatedGame.GetPath())
		return
	}

	if updatedGame.GetCover() != "/path/to/updated/test/cover.jpg" {
		t.Errorf("Expected cover to be '/path/to/updated/test/cover.jpg', but got: %s", updatedGame.GetCover())
		return
	}
}

func Test_it_should_not_be_able_to_update_a_game_that_does_not_exist(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	err := application_game.UpdateGame(application_game.UpdateGameInput{
		ID:       "nonexistent",
		Name:     "Test Game",
		Platform: "Test Platform",
		Path:     "/path/to/test/game",
		Cover:    "/path/to/test/cover.jpg",
	}, repository)

	if err == nil {
		t.Error("Expected an error, but got none")
		return
	}
}
