package platform_application_test

import (
	list_platforms "retrolauncher/backend/src/app/platform/application/list-platforms"
	"retrolauncher/backend/src/app/platform/domain"
	platform_doubles_test "retrolauncher/backend/tests/app/platform/doubles"
	"testing"

	"github.com/google/uuid"
)

func Test_it_should_be_able_to_list_platforms(t *testing.T) {
	repository := &platform_doubles_test.MemoryPlatformRepository{}
	platformType := domain.NewType(domain.TypeRetroArch)

	platform1 := domain.HydratePlatform(
		uuid.New(),
		"PlayStation 1",
		"/path/to/ps1",
		platformType,
	)

	platform2 := domain.HydratePlatform(
		uuid.New(),
		"Nintendo 64",
		"/path/to/n64",
		platformType,
	)

	repository.Save(platform1)
	repository.Save(platform2)

	sut := list_platforms.New(repository)
	result, err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if result == nil {
		t.Error("Expected result to be not nil")
		return
	}

	if len(result) != 2 {
		t.Errorf("Expected result to have 2 platforms, but got %d", len(result))
		return
	}

	// Verify that both platforms are in the result
	foundPlatforms := make(map[string]bool)
	for _, output := range result {
		foundPlatforms[output.Id] = true

		if output.Id != platform1.GetID().String() && output.Id != platform2.GetID().String() {
			t.Errorf("Unexpected platform ID in result: %s", output.Id)
			return
		}

		if output.Id == platform1.GetID().String() && output.Name != "PlayStation 1" {
			t.Errorf("Expected platform name to be 'PlayStation 1', but got '%s'", output.Name)
			return
		}

		if output.Id == platform2.GetID().String() && output.Name != "Nintendo 64" {
			t.Errorf("Expected platform name to be 'Nintendo 64', but got '%s'", output.Name)
			return
		}
	}
}

func Test_it_should_return_empty_list_when_no_platforms_exist(t *testing.T) {
	repository := &platform_doubles_test.MemoryPlatformRepository{}
	sut := list_platforms.New(repository)

	result, err := sut.Execute()

	if err != nil {
		t.Errorf("Expected no error, but got: %v", err)
		return
	}

	if result == nil {
		t.Error("Expected result to be not nil")
		return
	}

	if len(result) != 0 {
		t.Errorf("Expected result to be empty, but got %d platforms", len(result))
		return
	}
}
