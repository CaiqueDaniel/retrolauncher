package game_factories

import "retrolauncher/backend/src/app/games/domain/game"

type DefaultGameFactory struct{}

func (f *DefaultGameFactory) CreateGame(name, platform, path, cover string) *game.Game {
	return game.New(name, platform, path, cover)
}
