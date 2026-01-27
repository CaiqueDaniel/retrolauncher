package shared_services

import (
	"encoding/base64"
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

	if !l.fileSystem.ExistsFile(path) {
		return "", fmt.Errorf("file does not exist: %s", path)
	}

	copyPath, err := filepath.Abs(fmt.Sprintf("%s%s%s", internalImagePath, uuid.New().String(), extension))

	if err != nil {
		return "", err
	}

	err = l.fileSystem.CopyFile(path, copyPath)

	if err != nil {
		return "", err
	}

	return copyPath, nil
}

func (l *localImageUploader) RollbackCopy(path string) error {
	return l.fileSystem.RemoveFile(path)
}

func (l *localImageUploader) OpenAsBase64(path string) (string, error) {
	data, err := l.fileSystem.ReadFile(path)

	if err != nil {
		return "", err
	}

	encoded := base64.StdEncoding.EncodeToString(data)
	return encoded, nil
}
