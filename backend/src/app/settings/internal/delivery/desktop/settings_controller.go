package desktop

import (
	"retrolauncher/backend/src/app/settings/internal/application"
)

type SettingsController interface {
	Save(input SaveSettingsInputDto) string
	Get() (*application.GetSettingsOutput, error)
}

type settingsController struct {
	saveSettings application.SaveSettings
	getSettings  application.GetSettings
}

func NewSettingsController(saveSettings application.SaveSettings, getSettings application.GetSettings) SettingsController {
	return &settingsController{
		saveSettings: saveSettings,
		getSettings:  getSettings,
	}
}

func (sc *settingsController) Save(input SaveSettingsInputDto) string {
	err := sc.saveSettings.Execute(application.SaveSettingsInput{
		RetroarchFolderPath:      input.RetroarchFolderPath,
		RomsFolderPath:           input.RomsFolderPath,
		RetroachivementsUsername: input.RetroachivementsUsername,
		RetroachivementsPassword: input.RetroachivementsPassword,
		RetroachivementsApiKey:   input.RetroachivementsApiKey,
	})

	if err == nil {
		return ""
	}

	return err.Error()
}

func (sc *settingsController) Get() (*application.GetSettingsOutput, error) {
	return sc.getSettings.Execute()
}

type SaveSettingsInputDto struct {
	RetroarchFolderPath      string `json:"retroarchFolderPath"`
	RomsFolderPath           string `json:"romsFolderPath"`
	RetroachivementsUsername string `json:"retroachivementsUsername"`
	RetroachivementsPassword string `json:"retroachivementsPassword"`
	RetroachivementsApiKey   string `json:"retroachivementsApiKey"`
}
