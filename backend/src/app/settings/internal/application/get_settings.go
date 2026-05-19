package application

import (
	"retrolauncher/backend/src/app/settings/internal/domain"
)

type GetSettings interface {
	Execute() (*GetSettingsOutput, error)
}

type getSettings struct {
	settingsDAO        domain.SettingsDAO
	credentialsManager CredentialsManager
}

func NewGetSettings(settingsDAO domain.SettingsDAO, credentialsManager CredentialsManager) GetSettings {
	return &getSettings{
		settingsDAO:        settingsDAO,
		credentialsManager: credentialsManager,
	}
}

func (uc *getSettings) Execute() (*GetSettingsOutput, error) {
	settings, err := uc.settingsDAO.GetSettings()
	credentials := uc.getRetroAchievementsCredentials()

	if settings == nil {
		return &GetSettingsOutput{
			RetroarchFolderPath:      "",
			RomsFolderPath:           "",
			RetroachivementsUsername: "",
			RetroachivementsPassword: "",
			RetroachivementsApiKey:   "",
		}, nil
	}

	if err != nil {
		return nil, err
	}

	return &GetSettingsOutput{
		RetroarchFolderPath:      settings.RetroarchFolderPath,
		RomsFolderPath:           settings.RomsFolderPath,
		RetroachivementsUsername: settings.RetroAchievementsUsername,
		RetroachivementsPassword: credentials.Password,
		RetroachivementsApiKey:   credentials.ApiKey,
	}, nil
}

func (uc *getSettings) getRetroAchievementsCredentials() *retroAchievementsCredentials {
	password, err := uc.credentialsManager.GetCredentials("retroachivementsPassword")
	if err != nil {
		password = ""
	}

	apiKey, err := uc.credentialsManager.GetCredentials("retroachivementsApiKey")
	if err != nil {
		apiKey = ""
	}

	return &retroAchievementsCredentials{
		Password: password,
		ApiKey:   apiKey,
	}
}

type GetSettingsOutput struct {
	RetroarchFolderPath      string
	RomsFolderPath           string
	RetroachivementsUsername string
	RetroachivementsPassword string
	RetroachivementsApiKey   string
}

type retroAchievementsCredentials struct {
	Password string
	ApiKey   string
}
