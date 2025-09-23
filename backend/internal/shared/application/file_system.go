package shared_application

type FileSystem interface {
	SaveFile(path string, data []byte) error
	ReadFile(path string) ([]byte, error)
}
