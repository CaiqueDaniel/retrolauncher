package application_game_test

import (
	update_game "retrolauncher/backend/src/app/games/application"
	"retrolauncher/backend/src/app/games/domain/platform"
	game_factories "retrolauncher/backend/src/app/games/factories"
	shared_services "retrolauncher/backend/src/shared/services"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_update_a_game(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)
	factory := &game_factories.DefaultGameFactory{}

	fileSystem.SaveFile("/path/to/updated/test/cover.jpg", []byte("cover image data"))

	game, _ := factory.CreateGame("test", platform.New(platform.TypeRetroArch, "/path"), "test", "test")
	repository.Save(game)

	err := update_game.NewUpdateGame(repository, imageUploader).Execute(update_game.UpdateGameInput{
		ID:           game.GetId().String(),
		Name:         "Updated Test Game",
		PlatformType: platform.TypeRetroArch,
		PlatformPath: "/path/platform",
		Path:         "/path/to/updated/test/game",
		Cover:        "/path/to/updated/test/cover.jpg",
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

	if updatedGame.GetPlatformType().GetPlatformType() != platform.TypeRetroArch {
		t.Errorf("Expected platform to be '%s', but got: %s", platform.TypeRetroArch, updatedGame.GetPlatformType().GetPlatformType())
		return
	}

	if updatedGame.GetPath() != "/path/to/updated/test/game" {
		t.Errorf("Expected path to be '/path/to/updated/test/game', but got: %s", updatedGame.GetPath())
		return
	}

	if updatedGame.GetCover() != game.GetCover() {
		t.Errorf("Expected cover to be '/path/to/updated/test/cover.jpg', but got: %s", updatedGame.GetCover())
		return
	}
}

func Test_it_should_not_be_able_to_update_a_game_that_does_not_exist(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)

	err := update_game.NewUpdateGame(repository, imageUploader).Execute(update_game.UpdateGameInput{
		ID:           "nonexistent",
		Name:         "Test Game",
		PlatformType: platform.TypeRetroArch,
		Path:         "/path/to/test/game",
		Cover:        "/path/to/test/cover.jpg",
	})

	if err == nil {
		t.Error("Expected an error, but got none")
		return
	}
}
