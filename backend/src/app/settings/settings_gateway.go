package settings

import "retrolauncher/backend/src/app/settings/internal/application"

type settingsGateway struct {
	getSettings application.GetSettings
}

type SettingsService interface {
	GetSettings() (*Settings, error)
}

func NewSettingsGateway(getSettings application.GetSettings) SettingsService {
	return &settingsGateway{
		getSettings: getSettings,
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
