package application

type StartGameService interface {
	StartGame(gamePath, platformPath string) error
}
