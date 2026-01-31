package game_factories

import (
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
)

type DefaultGameFactory struct{}

func (f *DefaultGameFactory) CreateGame(name string, platform *platform.Platform, path, cover string) (*game.Game, []error) {
	return game.New(name, platform, path, cover)
}
