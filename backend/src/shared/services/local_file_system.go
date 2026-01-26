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
