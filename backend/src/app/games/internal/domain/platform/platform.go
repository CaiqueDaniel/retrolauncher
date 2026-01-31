package platform

import (
	"slices"
	"strings"
)

const (
	TypeRetroArch = "RetroArch"
)

type Platform struct {
	platformType string
	path         string
}

func New(platformType string, path string) *Platform {
	expectedValues := [...]string{TypeRetroArch}

	if !slices.Contains(expectedValues[:], platformType) {
		return nil
	}

	if "" == strings.TrimSpace(path) {
		return nil
	}

	return &Platform{
		platformType: platformType,
		path:         path,
	}
}

func (t *Platform) GetPlatformType() string {
	return t.platformType
}

func (t *Platform) GetPath() string {
	return t.path
}
