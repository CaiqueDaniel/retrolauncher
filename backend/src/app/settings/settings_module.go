package settings

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/delivery/desktop"
	"retrolauncher/backend/src/app/settings/internal/persistance"
	shared_services "retrolauncher/backend/src/shared/services"
)

type SettingsModule struct {
	SettingsController desktop.SettingsController
}

func NewSettingsModule() *SettingsModule {
	dao := persistance.NewStormSettingsDAO()
	fs := &shared_services.LocalFileSystem{}
	controller := desktop.NewSettingsController(
		application.NewSaveSettings(dao, fs),
		application.NewGetSettings(dao),
	)

	return &SettingsModule{
		SettingsController: controller,
	}
}
