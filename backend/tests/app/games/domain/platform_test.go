package game_test

import (
	"retrolauncher/backend/src/app/games/domain/platform"
	"testing"
)

func Test_it_should_be_able_to_create_with_valid_value(t *testing.T) {
	value := platform.New(platform.TypeRetroArch, "/path/core.dll")

	if value == nil {
		t.Error("should have created")
		return
	}
}

func Test_it_should_not_be_able_to_create_with_invalid_type(t *testing.T) {
	value := platform.New("invalid", "/path")

	if value != nil {
		t.Error("should not have created")
		return
	}
}

func Test_it_should_not_be_able_to_create_with_empty_path(t *testing.T) {
	value := platform.New(platform.TypeRetroArch, "")

	if value != nil {
		t.Error("should not have created")
		return
	}
}
