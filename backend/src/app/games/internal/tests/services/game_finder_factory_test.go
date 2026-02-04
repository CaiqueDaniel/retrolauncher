package services_test

import (
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

func Test_GameFinderFactory_CreateFrom(t *testing.T) {
	factory := services.NewGameFinderFactory()

	tests := []struct {
		platform       string
		expectedResult bool
	}{
		{"nes", true},
		{"snes", true},
		{"sfc", true},
		{"gb", true},
		{"gbc", true},
		{"n64", true},
		{"z64", true},
		{"sms", true},
		{"psx", true},
		{"cue", true},
		{"unknown", false},
		{"", false},
	}

	for _, tt := range tests {
		t.Run(tt.platform, func(t *testing.T) {
			service := factory.CreateFrom(tt.platform)
			if (service != nil) != tt.expectedResult {
				t.Errorf("CreateFrom(%s) returned service: %v, expected: %v", tt.platform, service != nil, tt.expectedResult)
			}
		})
	}
}
