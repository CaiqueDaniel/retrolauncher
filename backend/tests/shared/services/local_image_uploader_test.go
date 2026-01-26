package shared_services_local_test

import (
	shared_services "retrolauncher/backend/src/shared/services"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func TestCopyImageToInternalValidExtensions(t *testing.T) {
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

func TestCopyImageToInternalInvalidExtension(t *testing.T) {
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

func TestCopyImageToInternalPreservesExtension(t *testing.T) {
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
