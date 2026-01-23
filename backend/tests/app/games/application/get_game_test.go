package application_game_test

import (
	create_game "retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/get_game"
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/platform"
	game_factories "retrolauncher/backend/src/app/games/factories"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_get_a_game(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	factory := &game_factories.DefaultGameFactory{}

	// Create and save a game first
	err := create_game.New(factory, repository).Execute(create_game.Input{
		Name:         "Test Game",
		PlatformType: platform.TypeRetroArch,
		PlatformPath: "/path/platform",
		Path:         "/path/to/test/game",
		Cover:        "/path/to/test/cover.jpg",
	})

	if err != nil {
		t.Fatalf("Failed to setup test game: %v", err)
	}

	// Retrieve the saved game to get its ID (since ID is generated on creation)
	games := repository.List(domain.ListGamesParams{Name: "Test Game"})
	if len(games) != 1 {
		t.Fatal("Expected 1 game in repository")
	}
	existingGame := games[0]

	// Act: Try to get the game using the application use case
	sut := get_game.New(repository)

	foundGame, getErr := sut.Execute(get_game.Input{
		Id: existingGame.GetId().String(),
	})

	// Assert
	if getErr != nil {
		t.Errorf("Expected no error, but got: %v", getErr)
	}

	if foundGame == nil {
		t.Error("Expected to find a game, but got nil")
		return
	}

	if foundGame.Id != existingGame.GetId().String() {
		t.Errorf("Expected game ID %s, but got %s", existingGame.GetId().String(), foundGame.Id)
	}
}

func Test_it_should_not_be_able_to_get_a_game_that_does_not_exist(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}

	// Act: Try to get the game using the application use case
	foundGame, getErr := get_game.New(repository).Execute(get_game.Input{
		Id: "nonexistent-id",
	})

	// Assert
	if getErr == nil {
		t.Error("Expected an error, but got nil")
	}

	if foundGame != nil {
		t.Error("Expected to not find a game, but got a game")
	}
}
