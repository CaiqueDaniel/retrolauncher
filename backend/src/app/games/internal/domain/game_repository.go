package domain

import "retrolauncher/backend/src/app/games/internal/domain/game"

type GameRepository interface {
	Save(entity *game.Game) error
	Get(id string) (*game.Game, error)
	List(input ListGamesParams) []*game.Game
	Remove(entity *game.Game) error
}

type ListGamesParams struct {
	Name string
}
