package shared_services_local_test

import (
	"os"
	shared_services "retrolauncher/backend/internal/shared/services"
	"testing"
)

func Test_it_should_be_able_to_save_a_file(t *testing.T) {
	sut := &shared_services.LocalFileSystem{}
	err := sut.SaveFile("./tmp/test.txt", []byte("Hello, World!"))

	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}

	data, err := os.ReadFile("./tmp/test.txt")

	if err != nil {
		t.Errorf("Not able to read file")
	}

	if string(data) != "Hello, World!" {
		t.Errorf("File content does not match, got: %s", string(data))
	}

	os.RemoveAll("./tmp")
}

func Test_it_should_be_able_to_read_a_file(t *testing.T) {
	sut := &shared_services.LocalFileSystem{}

	sut.SaveFile("./tmp/test.txt", []byte("Hello, World!"))
	data, err := sut.ReadFile("./tmp/test.txt")

	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}

	if string(data) != "Hello, World!" {
		t.Errorf("File content does not match, got: %s", string(data))
	}

	os.RemoveAll("./tmp")
}
