package domain

import "retrolauncher/backend/src/app/games/domain/game"

type GameFactory interface {
	CreateGame(name, platform, path, cover string) *game.Game
}
