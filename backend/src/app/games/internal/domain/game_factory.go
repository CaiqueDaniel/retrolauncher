package domain

import (
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
)

type GameFactory interface {
	CreateGame(name string, platformType *platform.Platform, path, cover string) (*game.Game, []error)
}
