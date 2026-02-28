package application

type ImageToIcoService interface {
	CreateIcoFrom(path string) (string, error)
}
