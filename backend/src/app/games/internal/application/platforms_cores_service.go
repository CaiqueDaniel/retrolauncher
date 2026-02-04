package application

type PlatformsCoresService interface {
	GetPlatformsCores() (map[string]string, error)
}
