package game_test

import (
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform"
	"testing"

	"github.com/google/uuid"
)

func Test_it_should_be_able_to_create(t *testing.T) {
	game, _ := game.New("Test Game", platform.New(platform.TypeRetroArch, "/path"), "game.nes", "test_cover.jpg")

	if game.GetId() == (uuid.UUID{}) {
		t.Error("Game ID should not be empty")
	}

	if game.GetName() != "Test Game" {
		t.Error("Game name does not match")
	}

	if game.GetPlatformType().GetPlatformType() != "RetroArch" {
		t.Error("Game platform does not match")
	}

	if game.GetCover() != "test_cover.jpg" {
		t.Error("Game cover does not match")
	}

	if game.GetPath() != "game.nes" {
		t.Error("Game path does not match")
	}
}

func Test_it_should_be_able_to_hydrate(t *testing.T) {
	id := uuid.New()
	game := game.Hydrate(id, "Hydrated Game", platform.New(platform.TypeRetroArch, "/path"), "game.nes", "hydrated_cover.jpg")

	if game.GetId() != id {
		t.Error("Game ID does not match")
	}

	if game.GetName() != "Hydrated Game" {
		t.Error("Game name does not match")
	}

	if game.GetPlatformType().GetPlatformType() != "RetroArch" {
		t.Error("Game platform does not match")
	}

	if game.GetCover() != "hydrated_cover.jpg" {
		t.Error("Game cover does not match")
	}

	if game.GetPath() != "game.nes" {
		t.Error("Game path does not match")
	}
}

func Test_it_should_be_able_to_update(t *testing.T) {
	game, _ := game.New("Old Game", platform.New(platform.TypeRetroArch, "/path"), "game.nes", "old_cover.jpg")
	game.Update("Updated Game", platform.New(platform.TypeRetroArch, "/path"), "game.sns", "updated_cover.jpg")

	if game.GetName() != "Updated Game" {
		t.Error("Game name was not updated correctly")
	}

	if game.GetPlatformType().GetPlatformType() != "RetroArch" {
		t.Error("Game platform was not updated correctly")
	}

	if game.GetCover() != "updated_cover.jpg" {
		t.Error("Game cover was not updated correctly")
	}

	if game.GetPath() != "game.sns" {
		t.Error("Game path should remain unchanged after update")
	}
}

func Test_it_should_not_be_able_to_create_with_invalid_values(t *testing.T) {
	_, err := game.New("", platform.New("", "/path"), "", "")

	if err == nil {
		t.Error("Expected errors when creating game with invalid values, but got none.")
		return
	}

	if len(err) < 4 {
		t.Errorf("Expected at least 4 errors, but got: %d", len(err))
		return
	}
}
