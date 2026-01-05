package game_test

import (
	"retrolauncher/backend/src/app/games/domain/game"
	"testing"

	"github.com/google/uuid"
)

func Test_it_should_be_able_to_create(t *testing.T) {
	game := game.New("Test Game", "PC", "game.nes", "test_cover.jpg")

	if game.GetId() == (uuid.UUID{}) {
		t.Error("Game ID should not be empty")
	}

	if game.GetName() != "Test Game" {
		t.Error("Game name does not match")
	}

	if game.GetPlatform() != "PC" {
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
	game := game.Hydrate(id, "Hydrated Game", "Console", "game.nes", "hydrated_cover.jpg")

	if game.GetId() != id {
		t.Error("Game ID does not match")
	}

	if game.GetName() != "Hydrated Game" {
		t.Error("Game name does not match")
	}

	if game.GetPlatform() != "Console" {
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
	game := game.New("Old Game", "PC", "game.nes", "old_cover.jpg")
	game.Update("Updated Game", "Console", "game.sns", "updated_cover.jpg")

	if game.GetName() != "Updated Game" {
		t.Error("Game name was not updated correctly")
	}

	if game.GetPlatform() != "Console" {
		t.Error("Game platform was not updated correctly")
	}

	if game.GetCover() != "updated_cover.jpg" {
		t.Error("Game cover was not updated correctly")
	}

	if game.GetPath() != "game.sns" {
		t.Error("Game path should remain unchanged after update")
	}
}
