package domain

import "slices"

const (
	TypeRetroArch = "RetroArch"
)

type Type struct {
	value string
}

func NewType(value string) *Type {
	expectedValues := [...]string{TypeRetroArch}

	if !slices.Contains(expectedValues[:], value) {
		return nil
	}

	return &Type{value: value}
}
