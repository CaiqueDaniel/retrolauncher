package game_factories

import (
	"crypto/md5"
	"encoding/hex"
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
	return game.New(name, platform, path, cover, f.calculateHash(path))
}

func (f *DefaultGameFactory) CreateGameFromPath(path string, platformType *platform.Platform) (*game.Game, []error) {
	return game.New(f.fs.GetFileName(path), platformType, path, "", f.calculateHash(path))
}

func (f *DefaultGameFactory) calculateHash(path string) string {
	if f.fs != nil {
		data, err := f.fs.ReadFile(path)
		if err == nil && len(data) > 0 {
			hash := md5.Sum(data)
			return hex.EncodeToString(hash[:])
		}
	}
	hash := md5.Sum([]byte(path))
	return hex.EncodeToString(hash[:])
}
