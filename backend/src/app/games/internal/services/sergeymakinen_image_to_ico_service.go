package services

import (
	"bytes"
	"image"
	"image/jpeg"
	"image/png"
	"os"
	"path/filepath"
	"retrolauncher/backend/src/shared/application"

	"github.com/sergeymakinen/go-ico"
)

const internalImagePath = "./images/"

type sergeymakinenImageToIcoService struct {
	fs application.FileSystem
}

func NewSergeymakinenImageToIcoService(fs application.FileSystem) *sergeymakinenImageToIcoService {
	return &sergeymakinenImageToIcoService{fs}
}

func (s *sergeymakinenImageToIcoService) CreateIcoFrom(path string) (string, error) {
	extension := s.fs.GetFileExtension(path)
	data, err := s.fs.ReadFile(path)
	var image image.Image

	if err != nil {
		return "", err
	}

	if extension == ".png" {
		image, err = png.Decode(bytes.NewReader(data))
	}

	if extension == ".jpg" || extension == ".jpeg" {
		image, err = jpeg.Decode(bytes.NewReader(data))
	}

	if err != nil {
		return "", err
	}

	baseName := s.fs.GetFileName(path)
	baseName = baseName[:len(baseName)-len(extension)]
	icoPath := filepath.Join(internalImagePath, baseName+".ico")
	stream, err := os.Create(icoPath)

	if err != nil {
		return "", err
	}

	ico.Encode(stream, image)
	defer stream.Close()

	return icoPath, nil
}
