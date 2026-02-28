package services_test

import (
	"bytes"
	"image"
	"image/color"
	"image/jpeg"
	"image/png"
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/services"
	shared_services "retrolauncher/backend/src/shared/services"
	"testing"
)

// Test_integration_it_should_convert_png_to_ico é um teste de integração que
// verifica a conversão de uma imagem PNG real para ICO usando o filesystem real.
func Test_integration_it_should_convert_png_to_ico(t *testing.T) {
	tmpDir := t.TempDir()
	setupWorkDir(t, tmpDir)

	pngPath := filepath.Join(tmpDir, "cover.png")
	if err := createTestPNG(pngPath); err != nil {
		t.Fatalf("failed to create test PNG: %v", err)
	}

	fs := shared_services.NewLocalFileSystem()
	sut := services.NewSergeymakinenImageToIcoService(fs)
	icoPath, err := sut.CreateIcoFrom(pngPath)

	if err != nil {
		t.Fatalf("expected no error, got: %v", err)
	}

	assertIcoFileExists(t, icoPath, tmpDir)
}

func Test_integration_it_should_convert_jpg_to_ico(t *testing.T) {
	tmpDir := t.TempDir()
	setupWorkDir(t, tmpDir)

	jpgPath := filepath.Join(tmpDir, "cover.jpg")
	if err := createTestJPG(jpgPath); err != nil {
		t.Fatalf("failed to create test JPG: %v", err)
	}

	fs := shared_services.NewLocalFileSystem()
	sut := services.NewSergeymakinenImageToIcoService(fs)
	icoPath, err := sut.CreateIcoFrom(jpgPath)

	if err != nil {
		t.Fatalf("expected no error, got: %v", err)
	}

	assertIcoFileExists(t, icoPath, tmpDir)
}

func Test_integration_it_should_return_error_for_unreadable_file(t *testing.T) {
	fs := shared_services.NewLocalFileSystem()
	sut := services.NewSergeymakinenImageToIcoService(fs)

	_, err := sut.CreateIcoFrom("/does/not/exist/cover.png")

	if err == nil {
		t.Fatal("expected an error for a non-existent file, got nil")
	}
}

// setupWorkDir muda o diretório de trabalho para tmpDir e cria o subdiretório
// "images" que o serviço usa para gravar o arquivo .ico.
func setupWorkDir(t *testing.T, tmpDir string) {
	t.Helper()

	originalWd, err := os.Getwd()
	if err != nil {
		t.Fatalf("failed to get working directory: %v", err)
	}
	t.Cleanup(func() { os.Chdir(originalWd) })

	if err := os.Chdir(tmpDir); err != nil {
		t.Fatalf("failed to change working directory to %q: %v", tmpDir, err)
	}

	if err := os.Mkdir(filepath.Join(tmpDir, "images"), os.ModePerm); err != nil {
		t.Fatalf("failed to create images dir: %v", err)
	}
}

// assertIcoFileExists verifica que o arquivo .ico foi criado e tem conteúdo.
func assertIcoFileExists(t *testing.T, icoPath, tmpDir string) {
	t.Helper()

	if icoPath == "" {
		t.Fatal("expected a non-empty ico path, got empty string")
	}

	if icoPath != filepath.Join(tmpDir, "images", "cover.ico") {
		t.Fatalf("expected ico path to be %q, got %q", filepath.Join(tmpDir, "images", "cover.ico"), icoPath)
	}

	info, err := os.Stat(icoPath)
	if os.IsNotExist(err) {
		t.Fatalf("expected ico file to exist at %q, but it does not", icoPath)
	}

	if info.Size() == 0 {
		t.Errorf("expected ico file at %q to have content, but it is empty", icoPath)
	}
}

// createTestPNG gera um arquivo PNG 16x16 com cor sólida.
func createTestPNG(path string) error {
	img := image.NewRGBA(image.Rect(0, 0, 16, 16))
	for y := range 16 {
		for x := range 16 {
			img.Set(x, y, color.RGBA{R: 255, G: 100, B: 50, A: 255})
		}
	}

	var buf bytes.Buffer
	if err := png.Encode(&buf, img); err != nil {
		return err
	}

	return os.WriteFile(path, buf.Bytes(), 0644)
}

// createTestJPG gera um arquivo JPEG 16x16 com cor sólida.
func createTestJPG(path string) error {
	img := image.NewRGBA(image.Rect(0, 0, 16, 16))
	for y := range 16 {
		for x := range 16 {
			img.Set(x, y, color.RGBA{R: 50, G: 100, B: 200, A: 255})
		}
	}

	var buf bytes.Buffer
	if err := jpeg.Encode(&buf, img, nil); err != nil {
		return err
	}

	return os.WriteFile(path, buf.Bytes(), 0644)
}
