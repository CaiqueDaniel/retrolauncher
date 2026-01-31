package game_doubles_test

import (
	"retrolauncher/backend/src/app/games/internal/domain"
	"retrolauncher/backend/src/app/games/internal/domain/game"
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

func (r *MemoryGameRepository) Get(id string) (*game.Game, error) {
	return r.games[id], nil
}

func (r *MemoryGameRepository) List(input domain.ListGamesParams) []*game.Game {
	result := make([]*game.Game, 0)
	for _, game := range r.games {
		if input.Name != "" && game.GetName() != input.Name {
			continue
		}
		result = append(result, game)
	}

	return result
}

func (r *MemoryGameRepository) Size() int {
	return len(r.games)
}
