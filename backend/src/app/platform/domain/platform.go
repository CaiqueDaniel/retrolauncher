package domain

import (
	"errors"

	"github.com/google/uuid"
)

type Platform struct {
	id           uuid.UUID
	name         string
	path         string
	platformType *Type
}

func NewPlatform(Name, Path string, platformType Type) (*Platform, []error) {
	err := make([]error, 0)
	platform := &Platform{id: uuid.New()}
	platform.setName(Name, &err)
	platform.setPath(Path, &err)

	if len(err) > 0 {
		return nil, err
	}
	return platform, nil
}

func HydratePlatform(ID uuid.UUID, name, path string) *Platform {
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

func (p *Platform) setName(name string, err *[]error) {
	if name == "" {
		*err = append(*err, errors.New("name cannot be empty"))
		return
	}

	p.name = name
}

func (p *Platform) setPath(path string, err *[]error) {
	if path == "" {
		*err = append(*err, errors.New("path cannot be empty"))
		return
	}

	p.path = path
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
