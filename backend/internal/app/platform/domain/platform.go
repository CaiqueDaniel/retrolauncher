package platform

import "github.com/google/uuid"

type Platform struct {
	id   uuid.UUID
	name string
	path string
}

func New(name, path string) *Platform {
	return &Platform{
		name: name,
		path: path,
	}
}

func Hydrate(ID uuid.UUID, name, path string) *Platform {
	return &Platform{
		id:   ID,
		name: name,
		path: path,
	}
}

func (p *Platform) Update(Name, Path string) {
	p.name = Name
	p.path = Path
}

func (p *Platform) GetID() uuid.UUID {
	return p.id
}

func (p *Platform) GetName() string {
	return p.name
}

func (p *Platform) GetPath() string {
	return p.path
}
