package game

type GameFactory interface {
	CreateGame(name, platform, path, cover string) *Game
}
