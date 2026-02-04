package shared_services

import (
	"os"
	"path"
	"retrolauncher/backend/src/shared/application"
)

type LocalFileSystem struct {
}

func NewLocalFileSystem() application.FileSystem {
	return &LocalFileSystem{}
}

func (fs *LocalFileSystem) SaveFile(filepath string, data []byte) error {
	dir := path.Dir(filepath)
	err := os.MkdirAll(dir, os.ModePerm)

	if err != nil {
		return err
	}

	return os.WriteFile(filepath, data, 0644)
}

func (fs *LocalFileSystem) ReadFile(path string) ([]byte, error) {
	return os.ReadFile(path)
}

func (fs *LocalFileSystem) ExistsFile(path string) bool {
	_, err := os.Stat(path)
	return !os.IsNotExist(err)
}

func (fs *LocalFileSystem) CopyFile(src string, dst string) error {
	input, err := os.ReadFile(src)

	if err != nil {
		return err
	}

	dir := path.Dir(dst)
	err = os.MkdirAll(dir, os.ModePerm)

	if err != nil {
		return err
	}

	return os.WriteFile(dst, input, 0644)
}

func (fs *LocalFileSystem) RemoveFile(path string) error {
	return os.Remove(path)
}

func (fs *LocalFileSystem) ListFiles(path string) ([]string, error) {
	files, err := os.ReadDir(path)
	if err != nil {
		return nil, err
	}

	var fileNames []string
	for _, file := range files {
		if !file.IsDir() {
			fileNames = append(fileNames, file.Name())
		}
	}

	return fileNames, nil
}
