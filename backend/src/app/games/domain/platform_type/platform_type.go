package platform_type

import "slices"

const (
	TypeRetroArch = "RetroArch"
)

type PlatformType struct {
	value string
}

func New(value string) *PlatformType {
	expectedValues := [...]string{TypeRetroArch}

	if !slices.Contains(expectedValues[:], value) {
		return nil
	}

	return &PlatformType{value: value}
}

func (t *PlatformType) GetValue() string {
	return t.value
}
