package persistance

import (
	"retrolauncher/backend/src/app/settings/internal/domain"
	"retrolauncher/backend/src/app/settings/internal/domain/settings"
	"retrolauncher/backend/src/shared/persistance"
)

const (
	tableName = "settings.db"
	id        = "settings"
)

type stormSettingsDAO struct {
	driver *persistance.StormRepository[model]
}

func (s *stormSettingsDAO) GetSettings() (*settings.Settings, error) {
	model, err := s.driver.Get(id, tableName)

	if err != nil {
		return nil, err
	}

	if model == nil {
		return nil, nil
	}

	return &settings.Settings{
		RetroarchFolderPath: model.RetroarchFolderPath,
		RomsFolderPath:      model.RomsFolderPath,
	}, nil
}

func (s *stormSettingsDAO) SaveSettings(settings *settings.Settings) error {
	return s.driver.Save(&model{
		ID:                  id,
		RetroarchFolderPath: settings.RetroarchFolderPath,
		RomsFolderPath:      settings.RomsFolderPath,
	}, tableName)
}

type model struct {
	ID                  string `storm:"id"`
	RetroarchFolderPath string
	RomsFolderPath      string
}

func NewStormSettingsDAO(driver *persistance.StormRepository[model]) domain.SettingsDAO {
	return &stormSettingsDAO{
		driver: driver,
	}
}
