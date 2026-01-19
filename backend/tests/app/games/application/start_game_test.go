package application_game_test

import (
	"retrolauncher/backend/src/app/games/application/start_game"
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_not_be_able_to_start_a_game_that_does_not_exist(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	startGameService := &DoubleStartGameService{}

	sut := start_game.New(repository, startGameService)
	err := sut.Execute(start_game.Input{
		GameId: "nonexistent-id",
	})
	if err == nil {
		t.Error("Expected an error, but got nil")
		return
	}

	if startGameService.WasCalled {
		t.Error("Expected StartGameService not to be called")
		return
	}
}

func Test_it_should_start_a_game(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	startGameService := &DoubleStartGameService{}
	game, _ := game.New(
		"Test Game",
		platform.New(platform.TypeRetroArch, "/path/to/game"),
		"/path/to/game",
		"/path/to/platform",
	)

	repository.Save(game)

	sut := start_game.New(repository, startGameService)
	err := sut.Execute(start_game.Input{
		GameId: game.GetId().String(),
	})

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if !startGameService.WasCalled {
		t.Error("Expected StartGameService to be called")
		return
	}
}

type DoubleStartGameService struct {
	WasCalled bool
}

func (s *DoubleStartGameService) StartGame(gamePath, platformPath string) error {
	s.WasCalled = true
	return nil
}

func (s *DoubleStartGameService) Clear() {
	s.WasCalled = false
}
