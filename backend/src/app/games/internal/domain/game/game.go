package game

import (
	"errors"
	"retrolauncher/backend/src/app/games/internal/domain/platform"

	"github.com/google/uuid"
)

type Game struct {
	id           uuid.UUID
	name         string
	platformType *platform.Platform
	cover        string
	path         string
	hash         string
}

func New(Name string, PlatformType *platform.Platform, Path, Cover, Hash string) (*Game, []error) {
	err := make([]error, 0)
	game := &Game{id: uuid.New()}

	game.setName(Name, &err)
	game.setPlatformType(PlatformType, &err)
	game.setPath(Path, &err)
	game.setCover(Cover)
	game.setHash(Hash, &err)

	return game, err
}

func Hydrate(Id uuid.UUID, Name string, PlatformType *platform.Platform, Path, Cover, Hash string) *Game {
	return &Game{
		id:           Id,
		name:         Name,
		platformType: PlatformType,
		cover:        Cover,
		path:         Path,
		hash:         Hash,
	}
}

func (g *Game) Update(Name string, PlatformType *platform.Platform, Path, Cover string) []error {
	err := make([]error, 0)

	g.setName(Name, &err)
	g.setPlatformType(PlatformType, &err)
	g.setPath(Path, &err)
	g.setCover(Cover)

	return err
}

func (g *Game) GetId() uuid.UUID                    { return g.id }
func (g *Game) GetName() string                     { return g.name }
func (g *Game) GetPlatformType() *platform.Platform { return g.platformType }
func (g *Game) GetCover() string                    { return g.cover }
func (g *Game) GetPath() string                     { return g.path }
func (g *Game) GetHash() string                     { return g.hash }

func (g *Game) setName(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("name cannot be empty"))
		return
	}

	g.name = value
}

func (g *Game) setPlatformType(value *platform.Platform, err *[]error) {
	if value == nil || value.GetPlatformType() == "" {
		*err = append(*err, errors.New("invalid platform type"))
		return
	}

	g.platformType = value
}

func (g *Game) setCover(value string) {
	g.cover = value
}

func (g *Game) setPath(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("path cannot be empty"))
		return
	}
	g.path = value
}

func (g *Game) setHash(value string, err *[]error) {
	if value == "" {
		*err = append(*err, errors.New("hash cannot be empty"))
		return
	}
	g.hash = value
}
