package domain

import (
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform_type"
)

type GameFactory interface {
	CreateGame(name string, platformType *platform_type.PlatformType, path, cover string) *game.Game
}
