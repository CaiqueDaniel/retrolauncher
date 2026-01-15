package application_game_test

import (
	"retrolauncher/backend/src/app/games/application/list_games"
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_list_games(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	value, _ := game.New("Name", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover")
	repository.Save(value)
	sut := list_games.New(repository)

	result := sut.Execute(list_games.Input{
		Name: "",
	})

	if result == nil {
		t.Error("expected result to be not nil")
		return
	}

	if len(result) == 0 {
		t.Error("expected result to contain at least 1 game")
		return
	}
}

func Test_it_should_be_able_to_list_games_by_name(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	expectedGame, _ := game.New("Name", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover")
	currentGame, _ := game.New("Test", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover")

	repository.Save(expectedGame)
	repository.Save(currentGame)

	sut := list_games.New(repository)

	result := sut.Execute(list_games.Input{
		Name: "Name",
	})

	if result == nil {
		t.Error("expected result to be not nil")
		return
	}

	if len(result) == 0 {
		t.Error("expected result to contain games with name filter")
		return
	}

	if len(result) != 1 {
		t.Errorf("expected result to have 1 game, but got %d", len(result))
		return
	}

	if result[0].Id != expectedGame.GetId().String() {
		t.Errorf("expected game ID %s, but got %s", expectedGame.GetId().String(), result[0].Id)
		return
	}
}
