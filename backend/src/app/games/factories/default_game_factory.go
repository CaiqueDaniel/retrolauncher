package game_factories

import (
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform_type"
)

type DefaultGameFactory struct{}

func (f *DefaultGameFactory) CreateGame(name string, platform *platform_type.PlatformType, path, cover string) (*game.Game, []error) {
	return game.New(name, platform, path, cover)
}
