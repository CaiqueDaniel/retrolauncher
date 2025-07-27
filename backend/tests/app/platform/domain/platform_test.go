package platform_test

import (
	platform "retrolauncher/backend/internal/app/platform/domain"
	"testing"

	"github.com/google/uuid"
)

func Test_it_should_be_able_to_create(t *testing.T) {
	platform, _ := platform.New("Test Platform", "/path/to/test/platform")

	if platform == nil {
		t.Error("Expected platform to be created, but it is nil.")
		return
	}

	if platform.GetName() != "Test Platform" {
		t.Errorf("Expected platform name to be 'Test Platform', but got: %s", platform.GetName())
		return
	}

	if platform.GetPath() != "/path/to/test/platform" {
		t.Errorf("Expected platform path to be '/path/to/test/platform', but got: %s", platform.GetPath())
		return
	}
}

func Test_it_should_be_able_to_hydrate(t *testing.T) {
	id := uuid.New()
	platform := platform.Hydrate(id, "Hydrated Platform", "/path/to/hydrated/platform")

	if platform == nil {
		t.Error("Expected hydrated platform to be created, but it is nil.")
		return
	}

	if platform.GetID() != id {
		t.Errorf("Expected platform ID to be %v, but got: %v", id, platform.GetID())
		return
	}

	if platform.GetName() != "Hydrated Platform" {
		t.Errorf("Expected platform name to be 'Hydrated Platform', but got: %s", platform.GetName())
		return
	}

	if platform.GetPath() != "/path/to/hydrated/platform" {
		t.Errorf("Expected platform path to be '/path/to/hydrated/platform', but got: %s", platform.GetPath())
		return
	}
}

func Test_it_should_be_able_to_update(t *testing.T) {
	platform, _ := platform.New("Old Platform", "/path/to/old/platform")

	if platform == nil {
		t.Error("Expected platform to be created, but it is nil.")
		return
	}

	platform.Update("Updated Platform", "/path/to/updated/platform")

	if platform.GetName() != "Updated Platform" {
		t.Errorf("Expected platform name to be 'Updated Platform', but got: %s", platform.GetName())
		return
	}

	if platform.GetPath() != "/path/to/updated/platform" {
		t.Errorf("Expected platform path to be '/path/to/updated/platform', but got: %s", platform.GetPath())
		return
	}
}

func Test_it_should_not_be_able_to_create_with_empty_name(t *testing.T) {
	platform, err := platform.New("", "/path/to/test/platform")

	if platform != nil || err == nil {
		t.Error("Expected platform to be nil when created with an empty name, but it is not.")
		return
	}
}

func Test_it_should_not_be_able_to_create_with_empty_path(t *testing.T) {
	platform, err := platform.New("Test Platform", "")

	if platform != nil || err == nil {
		t.Error("Expected platform to be nil when created with an empty path, but it is not.")
		return
	}
}
