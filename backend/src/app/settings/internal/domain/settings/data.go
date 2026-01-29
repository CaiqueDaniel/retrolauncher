package settings

import "github.com/google/uuid"

type Settings struct {
	Id                  uuid.UUID
	RetroarchFolderPath string
	RomsFolderPath      string
}
