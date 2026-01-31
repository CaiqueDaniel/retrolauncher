package application_game_test

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	game_doubles_test "retrolauncher/backend/src/app/games/internal/tests/doubles"
	shared_services "retrolauncher/backend/src/shared/services"
	"testing"
)

func Test_it_should_be_able_to_list_games(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fs := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fs)

	expectedBase64 := "ZmFrZSBpbWFnZSBkYXRh"

	value, _ := game.New("Name", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover")
	fs.SaveFile(value.GetCover(), []byte("fake image data"))

	repository.Save(value)
	sut := application.NewListGames(repository, imageUploader)

	result := sut.Execute(application.ListGamesInput{
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

	if result[0].Cover != expectedBase64 {
		t.Errorf("expected game Cover %s, but got %s", expectedBase64, result[0].Cover)
		return
	}
}

func Test_it_should_be_able_to_list_games_by_name(t *testing.T) {
	repository := &game_doubles_test.MemoryGameRepository{}
	fs := game_doubles_test.NewMockFileSystem()
	imageUploader := shared_services.NewLocalImageUploader(fs)

	expectedGame, _ := game.New("Name", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover.jpg")
	currentGame, _ := game.New("Test", platform.New(platform.TypeRetroArch, "/path"), "Path", "Cover.jpg")
	expectedBase64 := "ZmFrZSBpbWFnZSBkYXRh"

	fs.SaveFile(expectedGame.GetCover(), []byte("fake image data"))

	repository.Save(expectedGame)
	repository.Save(currentGame)

	sut := application.NewListGames(repository, imageUploader)

	result := sut.Execute(application.ListGamesInput{
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

	if result[0].Cover != expectedBase64 {
		t.Errorf("expected game Cover %s, but got %s", expectedBase64, result[0].Cover)
		return
	}
}
