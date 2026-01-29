package application

import "retrolauncher/backend/src/app/settings/internal/domain"

type GetSettings interface {
	Execute() (*GetSettingsOutput, error)
}

type getSettings struct {
	settingsDAO domain.SettingsDAO
}

func NewGetSettings(settingsDAO domain.SettingsDAO) GetSettings {
	return &getSettings{
		settingsDAO: settingsDAO,
	}
}

func (uc *getSettings) Execute() (*GetSettingsOutput, error) {
	settings, err := uc.settingsDAO.GetSettings()

	if err != nil {
		return nil, err
	}

	return &GetSettingsOutput{
		RetroarchFolderPath: settings.RetroarchFolderPath,
		RomsFolderPath:      settings.RomsFolderPath,
	}, nil
}

type GetSettingsOutput struct {
	RetroarchFolderPath string
	RomsFolderPath      string
}
