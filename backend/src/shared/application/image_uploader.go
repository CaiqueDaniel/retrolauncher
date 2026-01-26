package application

type ImageUploader interface {
	CopyImageToInternal(path string) (string, error)
	RollbackCopy(path string) error
}
