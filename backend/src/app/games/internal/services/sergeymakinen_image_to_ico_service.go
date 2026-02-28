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
	"golang.org/x/image/draw"
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
	var img image.Image

	if err != nil {
		return "", err
	}

	if extension == ".png" {
		img, err = png.Decode(bytes.NewReader(data))
	}

	if extension == ".jpg" || extension == ".jpeg" {
		img, err = jpeg.Decode(bytes.NewReader(data))
	}

	if err != nil {
		return "", err
	}

	img = s.resizeIcon(img)
	baseName := s.fs.GetFileName(path)
	baseName = baseName[:len(baseName)-len(extension)]
	icoPath, err := filepath.Abs(filepath.Join(internalImagePath, baseName+".ico"))

	if err != nil {
		return "", err
	}

	stream, err := os.Create(icoPath)

	if err != nil {
		return "", err
	}

	err = ico.Encode(stream, img)

	defer stream.Close()
	return icoPath, err
}

func (s *sergeymakinenImageToIcoService) resizeIcon(img image.Image) image.Image {
	bounds := img.Bounds()

	if bounds.Dx() > 256 || bounds.Dy() > 256 {
		ratio := float64(bounds.Dx()) / float64(bounds.Dy())
		newW := 256
		newH := int(256 / ratio)
		if newH > 256 {
			newH = 256
			newW = int(256 * ratio)
		}

		dst := image.NewRGBA(image.Rect(0, 0, newW, newH))
		draw.CatmullRom.Scale(dst, dst.Rect, img, bounds, draw.Over, nil)
		img = dst
	}

	return img
}
