package services_test

import (
	"retrolauncher/backend/src/app/games/internal/services"
	"testing"
)

func Test_HardcodedPlatformsCoresService_GetPlatformsCores(t *testing.T) {
	service := services.NewHardcodedPlatformsCoresService()

	cores, err := service.GetPlatformsCores()

	if err != nil {
		t.Fatalf("Expected no error, got %v", err)
	}

	expectedCores := map[string]string{
		"nes": "mesen",
		"gb":  "sameboy",
		"gbc": "sameboy",
		"sfc": "snes",
		"n64": "mupen",
		"z64": "mupen",
		"sms": "smsplus",
		"cue": "mednafen",
	}

	if len(cores) != len(expectedCores) {
		t.Errorf("Expected %d cores, got %d", len(expectedCores), len(cores))
	}

	for platform, core := range expectedCores {
		if val, ok := cores[platform]; !ok || val != core {
			t.Errorf("Expected core for platform %s to be %s, got %s", platform, core, val)
		}
	}
}
