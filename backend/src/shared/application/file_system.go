package application

type FileSystem interface {
	SaveFile(path string, data []byte) error
	ReadFile(path string) ([]byte, error)
	ExistsFile(path string) bool
	CopyFile(src string, dst string) error
	RemoveFile(path string) error
}
