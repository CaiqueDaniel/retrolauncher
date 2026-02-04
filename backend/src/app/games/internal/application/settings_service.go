package application

type SettingsService interface {
	GetSettings() (*Settings, error)
}

type Settings struct {
	RetroarchFolderPath string
	RomsFolderPath      string
}
