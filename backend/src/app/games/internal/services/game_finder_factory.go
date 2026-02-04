package services

import "retrolauncher/backend/src/app/games/internal/application"

type gameFinderFactory struct{}

func NewGameFinderFactory() application.GameFinderFactory {
	return &gameFinderFactory{}
}

func (f *gameFinderFactory) CreateFrom(platformName string) application.GameFinderService {
	switch platformName {
	case "nes":
		return NewNESGameFinderService()
	case "snes", "sfc":
		return NewSNESGameFinderService()
	case "gb", "gbc":
		return NewGBGameFinderService()
	case "n64", "z64":
		return NewN64GameFinderService()
	case "sms":
		return NewSMSGameFinderService()
	case "psx", "cue":
		return NewPSXGameFinderService()
	default:
		return nil
	}
}
