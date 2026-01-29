package domain

import "retrolauncher/backend/src/app/settings/internal/domain/settings"

type SettingsDAO interface {
	GetSettings() (*settings.Settings, error)
	SaveSettings(settings *settings.Settings) error
}
