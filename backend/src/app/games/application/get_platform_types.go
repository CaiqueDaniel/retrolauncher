package application

import "retrolauncher/backend/src/app/games/domain/platform"

type GetPlatformTypes interface {
	Execute() []string
}

type getPlatformTypes struct{}

func NewGetPlatformTypes() GetPlatformTypes {
	return &getPlatformTypes{}
}

func (g *getPlatformTypes) Execute() []string {
	return []string{platform.TypeRetroArch}
}
