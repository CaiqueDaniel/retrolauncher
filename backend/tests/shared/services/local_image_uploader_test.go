package shared_services_local_test

import (
	shared_services "retrolauncher/backend/src/shared/services"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_copy_image_to_internal_valid_extensions(t *testing.T) {
	tests := []struct {
		name      string
		imagePath string
		extension string
	}{
		{
			name:      "jpg extension",
			imagePath: "/path/to/image.jpg",
			extension: ".jpg",
		},
		{
			name:      "jpeg extension",
			imagePath: "/path/to/image.jpeg",
			extension: ".jpeg",
		},
		{
			name:      "png extension",
			imagePath: "/path/to/image.png",
			extension: ".png",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			fs := game_doubles_test.NewMockFileSystem()
			uploader := shared_services.NewLocalImageUploader(fs)

			// Adicionar arquivo original ao mock
			fs.SaveFile(tt.imagePath, []byte("image data"))

			result, err := uploader.CopyImageToInternal(tt.imagePath)

			if err != nil {
				t.Fatalf("expected no error, got %v", err)
			}

			if result == "" {
				t.Fatalf("expected non-empty result, got empty string")
			}

			// Verificar se o arquivo foi copiado
			if !fs.ExistsFile(result) {
				t.Fatalf("expected file to be copied to %s", result)
			}
		})
	}
}

func Test_it_should_not_copy_image_with_invalid_extension(t *testing.T) {
	invalidExtensions := []string{
		"/path/to/file.txt",
		"/path/to/file.gif",
		"/path/to/file.bmp",
		"/path/to/file.pdf",
	}

	for _, imagePath := range invalidExtensions {
		t.Run(imagePath, func(t *testing.T) {
			fs := game_doubles_test.NewMockFileSystem()
			uploader := shared_services.NewLocalImageUploader(fs)

			result, err := uploader.CopyImageToInternal(imagePath)

			if err == nil {
				t.Fatalf("expected error for invalid extension, got nil")
			}

			if result != "" {
				t.Fatalf("expected empty result on error, got %s", result)
			}
		})
	}
}

func Test_it_should_copy_image_to_internal_preserves_extension(t *testing.T) {
	testCases := []struct {
		name      string
		imagePath string
	}{
		{"jpg file", "/path/to/image.jpg"},
		{"png file", "/path/to/image.png"},
		{"jpeg file", "/path/to/image.jpeg"},
	}

	for _, tt := range testCases {
		t.Run(tt.name, func(t *testing.T) {
			fs := game_doubles_test.NewMockFileSystem()
			uploader := shared_services.NewLocalImageUploader(fs)

			fs.SaveFile(tt.imagePath, []byte("image data"))

			result, err := uploader.CopyImageToInternal(tt.imagePath)

			if err != nil {
				t.Fatalf("expected no error, got %v", err)
			}

			// Verificar se a extensão foi preservada
			originalExt := tt.imagePath[len(tt.imagePath)-4:]
			resultExt := result[len(result)-4:]

			if resultExt != originalExt {
				t.Fatalf("expected extension %s, got %s", originalExt, resultExt)
			}
		})
	}
}

func Test_it_should_rollback_File(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	uploader := shared_services.NewLocalImageUploader(fs)

	filePath := "./images/test-image.jpg"
	fs.SaveFile(filePath, []byte("image data"))

	// Verificar que o arquivo existe
	if !fs.ExistsFile(filePath) {
		t.Fatalf("expected file to exist before rollback")
	}

	// Fazer rollback
	err := uploader.RollbackCopy(filePath)

	if err != nil {
		t.Fatalf("expected no error on rollback, got %v", err)
	}

	// Verificar que o arquivo foi removido
	if fs.ExistsFile(filePath) {
		t.Fatalf("expected file to be removed after rollback")
	}
}

func Test_it_should_rollback_nonexistent_file(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	uploader := shared_services.NewLocalImageUploader(fs)

	// Tentar remover arquivo que não existe
	err := uploader.RollbackCopy("./images/nonexistent.jpg")

	// Deve retornar sem erro (idempotente)
	if err != nil {
		t.Fatalf("expected no error on rollback of nonexistent file, got %v", err)
	}
}

func Test_it_should_not_copy_non_existent_file(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	uploader := shared_services.NewLocalImageUploader(fs)
	path, err := uploader.CopyImageToInternal("/path/to/nonexistent/image.jpg")

	if path != "" {
		t.Fatalf("expected empty path when copying nonexistent file, got %s", path)
	}

	if err == nil {
		t.Fatalf("expected error when copying nonexistent file, got nil")
	}
}

func Test_it_should_be_able_to_open_image_as_base64(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	uploader := shared_services.NewLocalImageUploader(fs)
	imagePath := "/path/to/image.jpg"
	imageData := []byte("fake image data")

	fs.SaveFile(imagePath, imageData)
	base64Str, err := uploader.OpenAsBase64(imagePath)

	if err != nil {
		t.Fatalf("expected no error, got %v", err)
	}

	expectedBase64 := "ZmFrZSBpbWFnZSBkYXRh"

	if base64Str != expectedBase64 {
		t.Fatalf("expected base64 %s, got %s", expectedBase64, base64Str)
	}
}
