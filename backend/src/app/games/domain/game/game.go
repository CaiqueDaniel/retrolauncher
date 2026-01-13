package game

import (
	"errors"
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

func New(Name string, PlatformType *platform_type.PlatformType, Path, Cover string) (*Game, []error) {
	err := make([]error, 0)
	game := &Game{id: uuid.New()}

	game.setName(Name, &err)
	game.setPlatformType(PlatformType, &err)
	game.setPath(Path, &err)
	game.setCover(Cover, &err)

	return game, err
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

func (g *Game) setName(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("name cannot be empty"))
		return
	}

	g.name = value
}

func (g *Game) setPlatformType(value *platform_type.PlatformType, err *[]error) {
	if value == nil || value.GetValue() == "" {
		*err = append(*err, errors.New("invalid platform type"))
		return
	}

	g.platformType = value
}

func (g *Game) setCover(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("cover cannot be empty"))
		return
	}
	g.cover = value
}

func (g *Game) setPath(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("path cannot be empty"))
		return
	}
	g.path = value
}
