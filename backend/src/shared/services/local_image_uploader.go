package shared_services

import (
	"fmt"
	"path/filepath"
	"retrolauncher/backend/src/shared/application"

	"github.com/google/uuid"
)

const internalImagePath = "./images/"

type localImageUploader struct {
	fileSystem application.FileSystem
}

func NewLocalImageUploader(fileSystem application.FileSystem) application.ImageUploader {
	return &localImageUploader{
		fileSystem: fileSystem,
	}
}

func (l *localImageUploader) CopyImageToInternal(path string) (string, error) {
	allowedExtensions := map[string]bool{
		".jpg":  true,
		".jpeg": true,
		".png":  true,
	}
	extension := filepath.Ext(path)

	if allowedExtensions[extension] == false {
		return "", fmt.Errorf("unsupported image extension: %s", extension)
	}

	copyPath := fmt.Sprintf("%s%s%s", internalImagePath, uuid.New().String(), extension)
	err := l.fileSystem.CopyFile(path, copyPath)

	if err != nil {
		return "", err
	}

	return copyPath, nil
}
