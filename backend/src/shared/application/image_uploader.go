package application

type ImageUploader interface {
	CopyImageToInternal(path string) (string, error)
}
