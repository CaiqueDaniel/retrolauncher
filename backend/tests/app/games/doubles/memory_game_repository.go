package game_doubles_test

import (
	"retrolauncher/backend/internal/app/games/domain/game"
)

type MemoryGameRepository struct {
	games map[string]*game.Game
}

func (r *MemoryGameRepository) Save(entity *game.Game) error {
	if r.games == nil {
		r.games = make(map[string]*game.Game)
	}
	r.games[entity.GetId().String()] = entity
	return nil
}

func (r *MemoryGameRepository) Size() int {
	return len(r.games)
}
