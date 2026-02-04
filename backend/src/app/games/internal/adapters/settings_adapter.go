package adapters

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/settings"
)

type settingsAdapter struct {
	gateway settings.SettingsService
}

func NewSettingsAdapter(gateway settings.SettingsService) application.SettingsService {
	return &settingsAdapter{
		gateway: gateway,
	}
}

func (a *settingsAdapter) GetSettings() (*application.Settings, error) {
	result, err := a.gateway.GetSettings()
	if err != nil {
		return nil, err
	}

	return &application.Settings{
		RetroarchFolderPath: result.RetroarchFolderPath,
		RomsFolderPath:      result.RomsFolderPath,
	}, nil
}
