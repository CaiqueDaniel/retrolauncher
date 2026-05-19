package settings

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/delivery/desktop"
	"retrolauncher/backend/src/app/settings/internal/persistance"
	"retrolauncher/backend/src/app/settings/internal/services"
	shared_services "retrolauncher/backend/src/shared/services"
)

type SettingsModule struct {
	SettingsController desktop.SettingsController
}

func NewSettingsModule() *SettingsModule {
	dao := persistance.NewStormSettingsDAO()
	fs := shared_services.NewLocalFileSystem()
	credentialsManager, err := services.NewKeyringCredentialsManager()

	if err != nil {
		panic(err)
	}

	controller := desktop.NewSettingsController(
		application.NewSaveSettings(dao, fs, credentialsManager),
		application.NewGetSettings(dao, credentialsManager),
	)

	return &SettingsModule{
		SettingsController: controller,
	}
}
