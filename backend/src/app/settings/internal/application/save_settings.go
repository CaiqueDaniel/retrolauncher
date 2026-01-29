package application

import (
	"errors"
	"retrolauncher/backend/src/app/settings/internal/domain"
	"retrolauncher/backend/src/app/settings/internal/domain/settings"
	"retrolauncher/backend/src/shared/application"
)

type SaveSettings interface {
	Execute(input SaveSettingsInput) error
}

type useCase struct {
	dao domain.SettingsDAO
	fs  application.FileSystem
}

func NewSaveSettings(dao domain.SettingsDAO, fs application.FileSystem) SaveSettings {
	return &useCase{
		dao: dao,
		fs:  fs,
	}
}

func (uc *useCase) Execute(input SaveSettingsInput) error {
	if !uc.fs.ExistsFile(input.RetroarchFolderPath) {
		return errors.New("retroarch folder not found")
	}

	if !uc.fs.ExistsFile(input.RomsFolderPath) {
		return errors.New("roms folder not found")
	}

	return uc.dao.SaveSettings(&settings.Settings{
		RetroarchFolderPath: input.RetroarchFolderPath,
		RomsFolderPath:      input.RomsFolderPath,
	})
}

type SaveSettingsInput struct {
	RetroarchFolderPath string
	RomsFolderPath      string
}
