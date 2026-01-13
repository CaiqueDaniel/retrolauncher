package domain

import "retrolauncher/backend/src/app/games/domain/game"

type GameRepository interface {
	Save(entity *game.Game) error
	Get(id string) (*game.Game, error)
	List(input ListGamesParams) []game.Game
}

type ListGamesParams struct {
	Name string
}
