package settings

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/persistance"
	"retrolauncher/backend/src/app/settings/internal/services"
)

type settingsGateway struct {
	getSettings application.GetSettings
}

type SettingsService interface {
	GetSettings() (*Settings, error)
}

func NewSettingsGateway() SettingsService {
	credentialsManager, err := services.NewKeyringCredentialsManager()

	if err != nil {
		panic(err)
	}

	return &settingsGateway{
		getSettings: application.NewGetSettings(
			persistance.NewStormSettingsDAO(),
			credentialsManager,
		),
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
	RetroarchFolderPath      string
	RomsFolderPath           string
	RetroachivementsUsername string
	RetroachivementsPassword string
	RetroachivementsApiKey   string
}
