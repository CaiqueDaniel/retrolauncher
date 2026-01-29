package desktop

import (
	"retrolauncher/backend/src/app/settings/internal/application"
)

type SettingsController interface {
	Save(input SaveSettingsInputDto) string
	Get() (*application.GetSettingsOutput, error)
}

type controller struct {
	saveSettings application.SaveSettings
	getSettings  application.GetSettings
}

func NewSettingsController(saveSettings application.SaveSettings, getSettings application.GetSettings) SettingsController {
	return &controller{
		saveSettings: saveSettings,
		getSettings:  getSettings,
	}
}

func (sc *controller) Save(input SaveSettingsInputDto) string {
	err := sc.saveSettings.Execute(application.SaveSettingsInput{
		RetroarchFolderPath: input.RetroarchFolderPath,
		RomsFolderPath:      input.RomsFolderPath,
	})

	return err.Error()
}

func (sc *controller) Get() (*application.GetSettingsOutput, error) {
	return sc.getSettings.Execute()
}

type SaveSettingsInputDto struct {
	RetroarchFolderPath string `json:"retroarchFolderPath"`
	RomsFolderPath      string `json:"romsFolderPath"`
}
