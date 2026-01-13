package game

import (
	"retrolauncher/backend/src/app/games/domain/platform_type"

	"github.com/google/uuid"
)

type Game struct {
	id           uuid.UUID
	name         string
	platformType *platform_type.PlatformType
	cover        string
	path         string
}

func New(Name string, PlatformType *platform_type.PlatformType, Path, Cover string) *Game {
	return &Game{
		id:           uuid.New(),
		name:         Name,
		platformType: PlatformType,
		cover:        Cover,
		path:         Path,
	}
}

func Hydrate(Id uuid.UUID, Name string, PlatformType *platform_type.PlatformType, Path, Cover string) *Game {
	return &Game{
		id:           Id,
		name:         Name,
		platformType: PlatformType,
		cover:        Cover,
		path:         Path,
	}
}

func (g *Game) Update(Name string, PlatformType *platform_type.PlatformType, Path, Cover string) {
	g.name = Name
	g.platformType = PlatformType
	g.cover = Cover
	g.path = Path
}

func (g *Game) GetId() uuid.UUID                             { return g.id }
func (g *Game) GetName() string                              { return g.name }
func (g *Game) GetPlatformType() *platform_type.PlatformType { return g.platformType }
func (g *Game) GetCover() string                             { return g.cover }
func (g *Game) GetPath() string                              { return g.path }
