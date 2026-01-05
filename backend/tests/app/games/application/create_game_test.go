package application_game_test

import (
	"retrolauncher/backend/src/app/games/application/create_game"
	game_factories "retrolauncher/backend/src/app/games/factories"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_create_a_game(t *testing.T) {
	factory := &game_factories.DefaultGameFactory{}
	repository := &game_doubles_test.MemoryGameRepository{}
	sut := create_game.New(factory, repository)

	err := sut.Execute(create_game.Input{
		Name:     "Test Game",
		Platform: "Test Platform",
		Path:     "/path/to/test/game",
		Cover:    "/path/to/test/cover.jpg",
	})

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if repository.Size() == 0 {
		t.Error("Expected repository to have at least one game, but it is empty.")
		return
	}
}
