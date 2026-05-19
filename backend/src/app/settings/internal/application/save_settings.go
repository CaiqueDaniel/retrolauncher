package application

import (
	"errors"
	"fmt"
	"retrolauncher/backend/src/app/settings/internal/domain"
	"retrolauncher/backend/src/app/settings/internal/domain/settings"
	"retrolauncher/backend/src/shared/application"
)

type SaveSettings interface {
	Execute(input SaveSettingsInput) error
}

type useCase struct {
	dao                domain.SettingsDAO
	fs                 application.FileSystem
	credentialsManager CredentialsManager
}

func NewSaveSettings(
	dao domain.SettingsDAO,
	fs application.FileSystem,
	credentialsManager CredentialsManager,
) SaveSettings {
	return &useCase{
		dao:                dao,
		fs:                 fs,
		credentialsManager: credentialsManager,
	}
}

func (uc *useCase) Execute(input SaveSettingsInput) error {
	if !uc.fs.ExistsFile(input.RetroarchFolderPath) {
		return errors.New("retroarch folder not found")
	}

	if !uc.fs.ExistsFile(input.RomsFolderPath) {
		return errors.New("roms folder not found")
	}

	if input.RetroachivementsUsername != "" {
		err := uc.credentialsManager.SaveCredentials("retroachivementsUsername", input.RetroachivementsUsername)
		fmt.Println("err1", err)
		if err != nil {
			return err
		}
	}

	if input.RetroachivementsPassword != "" {
		err := uc.credentialsManager.SaveCredentials("retroachivementsPassword", input.RetroachivementsPassword)
		if err != nil {
			return err
		}
	}

	if input.RetroachivementsApiKey != "" {
		err := uc.credentialsManager.SaveCredentials("retroachivementsApiKey", input.RetroachivementsApiKey)
		if err != nil {
			return err
		}
	}

	return uc.dao.SaveSettings(&settings.Settings{
		RetroarchFolderPath: input.RetroarchFolderPath,
		RomsFolderPath:      input.RomsFolderPath,
	})
}

type SaveSettingsInput struct {
	RetroarchFolderPath      string
	RomsFolderPath           string
	RetroachivementsUsername string
	RetroachivementsPassword string
	RetroachivementsApiKey   string
}
