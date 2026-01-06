package platform_application_test

import (
	"retrolauncher/backend/src/app/platform/application/get_platform_types"
	"testing"
)

func Test_it_should_be_able_to_get_platform_types(t *testing.T) {
	results := get_platform_types.New().Execute()

	if len(*results) != 1 {
		t.Error("Expected 1 platform type, but got", len(*results))
		return
	}
}
