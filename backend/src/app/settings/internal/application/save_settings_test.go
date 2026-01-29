package application_test

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/domain/settings"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_save_settings(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs)

	fs.SaveFile("/path/to/retroarch", []byte("data"))
	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath: "/path/to/retroarch",
		RomsFolderPath:      "/path/to/roms",
	})

	if err != nil {
		t.Error("Expected no error, got", err)
	}
}

func Test_it_should_be_able_to_return_error_given_no_retroarch_folder_was_found(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs)

	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath: "/path/to/retroarch",
		RomsFolderPath:      "/path/to/roms",
	})

	if err == nil {
		t.Error("Expected no error, got", err)
	}

	if err.Error() != "retroarch folder not found" {
		t.Error("Expected 'retroarch folder not found' error, got", err.Error())
	}
}

func Test_it_should_be_able_to_return_error_given_no_roms_folder_was_found(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs)

	fs.SaveFile("/path/to/retroarch", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath: "/path/to/retroarch",
		RomsFolderPath:      "/path/to/roms",
	})

	if err == nil {
		t.Error("Expected no error, got", err)
	}

	if err.Error() != "roms folder not found" {
		t.Error("Expected 'roms folder not found' error, got", err.Error())
	}
}

type settingsDAOMock struct {
	savedSettings *settings.Settings
}

func (dao *settingsDAOMock) SaveSettings(settings *settings.Settings) error {
	dao.savedSettings = settings
	return nil
}

func (dao *settingsDAOMock) GetSettings() (*settings.Settings, error) {
	return dao.savedSettings, nil
}
