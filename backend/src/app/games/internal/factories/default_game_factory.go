package game_factories

import (
	"retrolauncher/backend/src/app/games/internal/domain"
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	"retrolauncher/backend/src/shared/application"
)

type DefaultGameFactory struct {
	fs application.FileSystem
}

func NewDefaultGameFactory(fs application.FileSystem) domain.GameFactory {
	return &DefaultGameFactory{
		fs: fs,
	}
}

func (f *DefaultGameFactory) CreateGame(name string, platform *platform.Platform, path, cover string) (*game.Game, []error) {
	return game.New(name, platform, path, cover)
}

func (f *DefaultGameFactory) CreateGameFromPath(path string, platformType *platform.Platform) (*game.Game, []error) {
	return game.New(f.fs.GetFileName(path), platformType, path, "")
}
