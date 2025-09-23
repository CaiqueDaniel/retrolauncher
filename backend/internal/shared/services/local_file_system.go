package shared_services

import (
	"os"
	"path"
)

type LocalFileSystem struct {
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
