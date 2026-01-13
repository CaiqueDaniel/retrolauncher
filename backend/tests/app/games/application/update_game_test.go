package application_game_test

import (
	"retrolauncher/backend/src/app/games/application/update_game"
	"retrolauncher/backend/src/app/games/domain/platform_type"
	game_factories "retrolauncher/backend/src/app/games/factories"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_update_a_game(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	factory := &game_factories.DefaultGameFactory{}
	game, _ := factory.CreateGame("test", platform_type.New(platform_type.TypeRetroArch), "test", "test")
	repository.Save(game)

	err := update_game.New(repository).Execute(update_game.Input{
		ID:       game.GetId().String(),
		Name:     "Updated Test Game",
		Platform: platform_type.TypeRetroArch,
		Path:     "/path/to/updated/test/game",
		Cover:    "/path/to/updated/test/cover.jpg",
	})

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

	if updatedGame.GetPlatformType().GetValue() != platform_type.TypeRetroArch {
		t.Errorf("Expected platform to be 'RetroArch', but got: %s", updatedGame.GetPlatformType().GetValue())
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
	err := update_game.New(repository).Execute(update_game.Input{
		ID:       "nonexistent",
		Name:     "Test Game",
		Platform: platform_type.TypeRetroArch,
		Path:     "/path/to/test/game",
		Cover:    "/path/to/test/cover.jpg",
	})

	if err == nil {
		t.Error("Expected an error, but got none")
		return
	}
}
