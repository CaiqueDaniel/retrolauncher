package shared_services_local_test

import (
	"os"
	shared_services "retrolauncher/backend/src/shared/services"
	"testing"
)

func Test_it_should_be_able_to_save_a_file(t *testing.T) {
	sut := shared_services.NewLocalFileSystem()
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
	sut := shared_services.NewLocalFileSystem()

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

func Test_it_should_check_if_file_exists(t *testing.T) {
	sut := shared_services.NewLocalFileSystem()
	sut.SaveFile("./tmp/test.txt", []byte("Hello, World!"))

	exists := sut.ExistsFile("./tmp/test.txt")

	if !exists {
		t.Errorf("Expected file to exist, but it doesn't")
	}

	os.RemoveAll("./tmp")
}

func Test_it_should_copy_a_file(t *testing.T) {
	sut := shared_services.NewLocalFileSystem()
	sut.SaveFile("./tmp/source.txt", []byte("Hello, World!"))
	err := sut.CopyFile("./tmp/source.txt", "./tmp/destination.txt")

	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}
	data, err := sut.ReadFile("./tmp/destination.txt")

	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}
	if string(data) != "Hello, World!" {
		t.Errorf("File content does not match, got: %s", string(data))
	}
	os.RemoveAll("./tmp")
}

func Test_it_should_list_files_in_a_directory(t *testing.T) {
	sut := shared_services.NewLocalFileSystem()
	sut.SaveFile("./tmp/test.txt", []byte("Hello, World!"))
	sut.SaveFile("./tmp/test2.txt", []byte("Hello, World!"))

	files, err := sut.ListFiles("./tmp")

	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}

	if len(files) != 2 {
		t.Errorf("Expected 2 files, but got %d", len(files))
	}

	os.RemoveAll("./tmp")
}

func Test_it_should_get_file_name(t *testing.T) {
	sut := shared_services.NewLocalFileSystem()
	fileName := sut.GetFileName("./tmp/test.txt")

	if fileName != "test.txt" {
		t.Errorf("Expected test.txt, but got %s", fileName)
	}
}
