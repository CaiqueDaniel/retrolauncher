package application_game_test

import (
	"retrolauncher/backend/src/app/games/application/list_games"
	"retrolauncher/backend/src/app/games/domain/game"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_list_games(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	repository.Save(game.New("Name", "Platform", "Path", "Cover"))
	sut := list_games.New(repository)

	result := sut.Execute(list_games.Input{
		Name: "",
	})

	if result == nil {
		t.Error("expected result to be not nil")
		return
	}

	if len(result) == 0 {
		t.Error("expected result to have 1 game")
		return
	}
}

func Test_it_should_be_able_to_list_games_by_name(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	expectedGame := game.New("Name", "Platform", "Path", "Cover")

	repository.Save(expectedGame)
	repository.Save(game.New("Test", "Platform", "Path", "Cover"))

	sut := list_games.New(repository)

	result := sut.Execute(list_games.Input{
		Name: "Name",
	})

	if result == nil {
		t.Error("expected result to be not nil")
		return
	}

	if len(result) == 0 {
		t.Error("expected result to have 1 game")
		return
	}

	if len(result) != 1 {
		t.Error("expected result to have 1 game")
		return
	}

	if result[0].Id != expectedGame.GetId().String() {
		t.Error("expected result to have 1 game")
		return
	}
}
