package platform

import "slices"

const (
	TypeRetroArch = "RetroArch"
)

type Platform struct {
	value string
}

func New(value string) *Platform {
	expectedValues := [...]string{TypeRetroArch}

	if !slices.Contains(expectedValues[:], value) {
		return nil
	}

	return &Platform{value: value}
}

func (t *Platform) GetValue() string {
	return t.value
}
