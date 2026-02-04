package services

import "retrolauncher/backend/src/app/games/internal/application"

type hardcodedPlatformsCoresService struct {
}

func NewHardcodedPlatformsCoresService() application.PlatformsCoresService {
	return &hardcodedPlatformsCoresService{}
}

func (s *hardcodedPlatformsCoresService) GetPlatformsCores() (map[string]string, error) {
	return map[string]string{
		"nes": "mesen",
		"gb":  "sameboy",
		"gbc": "sameboy",
		"sfc": "snes",
		"n64": "mupen",
		"z64": "mupen",
		"sms": "smsplus",
		"cue": "mednafen",
	}, nil
}
