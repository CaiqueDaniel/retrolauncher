package game

import (
	"github.com/google/uuid"
)

type Game struct {
	id       uuid.UUID
	name     string
	platform string
	cover    string
	path     string
}

func New(Name, Platform, Path, Cover string) *Game {
	return &Game{
		id:       uuid.New(),
		name:     Name,
		platform: Platform,
		cover:    Cover,
		path:     Path,
	}
}

func Hydrate(Id uuid.UUID, Name, Platform, Path, Cover string) *Game {
	return &Game{
		id:       Id,
		name:     Name,
		platform: Platform,
		cover:    Cover,
		path:     Path,
	}
}

func (g *Game) Update(Name, Platform, Path, Cover string) {
	g.name = Name
	g.platform = Platform
	g.cover = Cover
	g.path = Path
}

func (g *Game) GetId() uuid.UUID    { return g.id }
func (g *Game) GetName() string     { return g.name }
func (g *Game) GetPlatform() string { return g.platform }
func (g *Game) GetCover() string    { return g.cover }
func (g *Game) GetPath() string     { return g.path }
