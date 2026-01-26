package persistance

import (
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/app/games/domain/game"
	"retrolauncher/backend/src/app/games/domain/platform"
	"retrolauncher/backend/src/shared/persistance"

	"github.com/google/uuid"
)

const table_name = "games.db"

type StormGameRepository struct {
	driver *persistance.StormRepository[model]
}

func (r *StormGameRepository) Save(entity *game.Game) error {
	return r.driver.Save(&model{
		Id:           entity.GetId().String(),
		Name:         entity.GetName(),
		PlatformType: entity.GetPlatformType().GetPlatformType(),
		PlatformPath: entity.GetPlatformType().GetPath(),
		Cover:        entity.GetCover(),
		Path:         entity.GetPath(),
	}, table_name)
}

func (r *StormGameRepository) Get(id string) (*game.Game, error) {
	model, err := r.driver.Get(id, table_name)

	if err != nil {
		return nil, err
	}

	if model == nil {
		return nil, nil
	}

	return toDomain(model), nil
}

func (r *StormGameRepository) List(input domain.ListGamesParams) []*game.Game {
	models, err := r.driver.List("Name", input.Name, table_name)

	if err != nil {
		return []*game.Game{}
	}

	var games []*game.Game

	for _, model := range models {
		games = append(games, toDomain(model))
	}
	return games
}

func toDomain(model *model) *game.Game {
	gameId, _ := uuid.Parse(model.Id)
	platform := platform.New(model.PlatformType, model.PlatformPath)

	return game.Hydrate(
		gameId,
		model.Name,
		platform,
		model.Path,
		model.Cover,
	)
}

type model struct {
	Id           string `storm:"id"`
	Name         string
	PlatformType string
	PlatformPath string
	Cover        string
	Path         string
}
