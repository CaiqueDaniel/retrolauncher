package desktop

import (
	"retrolauncher/backend/src/app/settings/internal/application"
)

type SettingsController interface {
	Save(input SaveSettingsInputDto) string
	//Get
}

type controller struct {
	saveSettings application.SaveSettings
}

func NewSettingsController(saveSettings application.SaveSettings) SettingsController {
	return &controller{
		saveSettings: saveSettings,
	}
}

func (sc *controller) Save(input SaveSettingsInputDto) string {
	err := sc.saveSettings.Execute(application.SaveSettingsInput{
		RetroarchFolderPath: input.RetroarchFolderPath,
		RomsFolderPath:      input.RomsFolderPath,
	})

	return err.Error()
}

type SaveSettingsInputDto struct {
	RetroarchFolderPath string `json:"retroarchFolderPath"`
	RomsFolderPath      string `json:"romsFolderPath"`
}
