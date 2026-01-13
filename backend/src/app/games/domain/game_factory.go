package domain

import (
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform"
)

type GameFactory interface {
	CreateGame(name string, platformType *platform.Platform, path, cover string) (*game.Game, []error)
}
