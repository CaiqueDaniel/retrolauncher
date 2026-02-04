package settings

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/persistance"
)

type settingsGateway struct {
	getSettings application.GetSettings
}

type SettingsService interface {
	GetSettings() (*Settings, error)
}

func NewSettingsGateway() SettingsService {
	return &settingsGateway{
		getSettings: application.NewGetSettings(persistance.NewStormSettingsDAO()),
	}
}

func (s *settingsGateway) GetSettings() (*Settings, error) {
	result, err := s.getSettings.Execute()

	if err != nil {
		return nil, err
	}

	return &Settings{
		RetroarchFolderPath: result.RetroarchFolderPath,
		RomsFolderPath:      result.RomsFolderPath,
	}, nil
}

type Settings struct {
	RetroarchFolderPath string
	RomsFolderPath      string
}
