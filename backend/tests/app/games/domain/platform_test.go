package game_test

import (
	"retrolauncher/backend/src/app/games/domain/platform"
	"testing"
)

func Test_it_should_be_able_to_create_with_valid_value(t *testing.T) {
	value := platform.New(platform.TypeRetroArch, "/path")

	if value == nil {
		t.Error("should have created")
		return
	}
}

func Test_it_should_not_be_able_to_create_with_invalid_value(t *testing.T) {
	value := platform.New("invalid", "")

	if value != nil {
		t.Error("should not have created")
		return
	}
}
