package application

type GameFinderService interface {
	GetFilesFrom(folder string) []string
}

type GameFinderFactory interface {
	CreateFrom(platformName string) GameFinderService
}
