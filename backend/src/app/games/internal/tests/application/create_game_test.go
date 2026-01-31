package application_game_test

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	game_factories "retrolauncher/backend/src/app/games/internal/factories"
	game_doubles_test "retrolauncher/backend/src/app/games/internal/tests/doubles"
	shared_services "retrolauncher/backend/src/shared/services"
	"testing"
)

func Test_it_should_be_able_to_create_a_game(t *testing.T) {
	factory := &game_factories.DefaultGameFactory{}
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)

	fileSystem.SaveFile("/path/to/test/cover.jpg", []byte("cover image data"))

	sut := application.NewCreateGame(factory, repository, imageUploader)

	err := sut.Execute(application.CreateGameInput{
		Name:         "Test Game",
		PlatformType: platform.TypeRetroArch,
		PlatformPath: "/path/to/platform",
		Path:         "/path/to/test/game",
		Cover:        "/path/to/test/cover.jpg",
	})

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() == 0 {
		t.Error("Expected repository to have at least one game, but it is empty.")
		return
	}

	if len(fileSystem.ListFiles()) < 2 {
		t.Error("Expected game file to exist in the file system, but it does not.")
		return
	}
}

func Test_it_should_rollback_copied_image_if_game_creation_fails(t *testing.T) {
	factory := &game_factories.DefaultGameFactory{}
	repository := &game_doubles_test.MemoryGameRepository{}
	fileSystem := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fileSystem)
	fileSystem.SaveFile("/path/to/test/cover.png", []byte("cover image data"))

	sut := application.NewCreateGame(factory, repository, imageUploader)
	err := sut.Execute(application.CreateGameInput{
		Name:         "", // Invalid name to trigger creation failure
		PlatformType: platform.TypeRetroArch,
		PlatformPath: "/path/to/platform",
		Path:         "/path/to/test/game",
		Cover:        "/path/to/test/cover.png",
	})

	if err == nil {
		t.Error("Expected error due to invalid game name, but got none.")
		return
	}

	if repository.Size() != 0 {
		t.Error("Expected repository to be empty due to rollback, but it has games.")
		return
	}

	if len(fileSystem.ListFiles()) == 2 {
		t.Error("Expected no files in the file system due to rollback, but some exist.")
		return
	}
}
