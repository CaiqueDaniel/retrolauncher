package platform_application_test

import (
	platform_application "retrolauncher/backend/src/app/platform/application"
	platform_factories "retrolauncher/backend/src/app/platform/factories"
	platform_doubles_test "retrolauncher/backend/tests/app/platform/doubles"
	"testing"
)

func Test_it_should_be_able_to_create_platform(t *testing.T) {
	factory := &platform_factories.DefaultPlatformFactory{}
	repository := &platform_doubles_test.MemoryPlatformRepository{}

	platform_application.CreatePlatform(platform_application.CreatePlatformInput{
		Name: "Test Platform",
		Path: "/path/to/test/platform",
	}, factory, repository)

	if repository.Size() == 0 {
		t.Error("Expected repository to have at least one platform, but it is empty.")
		return
	}
}

func Test_it_should_be_able_to_propagate_validation_errors(t *testing.T) {
	factory := &platform_factories.DefaultPlatformFactory{}
	repository := &platform_doubles_test.MemoryPlatformRepository{}

	errors := platform_application.CreatePlatform(platform_application.CreatePlatformInput{
		Name: "", // Invalid name
		Path: "",
	}, factory, repository)

	if len(errors) == 0 {
		t.Error("Expected validation errors, but got none.")
		return
	}

	if repository.Size() != 0 {
		t.Error("Expected repository to remain empty due to validation errors, but it has entries.")
		return
	}

	for _, err := range errors {
		if err == nil {
			t.Error("Expected non-nil validation error, but got nil.")
			return
		}
	}

	if len(errors) != 2 {
		t.Errorf("Expected 2 validation errors, but got %d", len(errors))
		return
	}

	if errors[0].Error() != "name cannot be empty" {
		t.Errorf("Expected first error to be 'name cannot be empty', but got '%s'", errors[0].Error())
		return
	}

	if errors[1].Error() != "path cannot be empty" {
		t.Errorf("Expected second error to be 'path cannot be empty', but got '%s'", errors[1].Error())
		return
	}

	if repository.Size() != 0 {
		t.Error("Expected repository to remain empty due to validation errors, but it has entries.")
		return
	}
}
