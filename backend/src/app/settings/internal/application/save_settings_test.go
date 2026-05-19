package application_test

import (
	"retrolauncher/backend/src/app/settings/internal/application"
	"retrolauncher/backend/src/app/settings/internal/domain/settings"
	game_doubles_test "retrolauncher/backend/tests/app/games/doubles"
	"testing"
)

func Test_it_should_be_able_to_save_retorarch_settings(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{}
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs, credentialsManager)

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

func Test_it_should_be_able_to_save_retroachivements_settings(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{items: make(map[string]string)}
	dao := &settingsDAOMock{}
	sut := application.NewSaveSettings(dao, fs, credentialsManager)

	fs.SaveFile("/path/to/retroarch", []byte("data"))
	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath:      "/path/to/retroarch",
		RomsFolderPath:           "/path/to/roms",
		RetroachivementsUsername: "user",
		RetroachivementsPassword: "password",
		RetroachivementsApiKey:   "apiKey",
	})

	if err != nil {
		t.Error("Expected no error, got", err)
	}

	if dao.savedSettings.RetroAchievementsUsername == "" {
		t.Error("Expected saved credentials, got empty username")
	}

	if credentialsManager.items["retroachivementsPassword"] == "" {
		t.Error("Expected saved credentials, got empty password")
	}

	if credentialsManager.items["retroachivementsApiKey"] == "" {
		t.Error("Expected saved credentials, got empty api key")
	}
}

func Test_it_should_be_able_to_save_retroachivements_username(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{items: make(map[string]string)}
	dao := &settingsDAOMock{}
	sut := application.NewSaveSettings(dao, fs, credentialsManager)

	fs.SaveFile("/path/to/retroarch", []byte("data"))
	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath:      "/path/to/retroarch",
		RomsFolderPath:           "/path/to/roms",
		RetroachivementsUsername: "user",
		RetroachivementsPassword: "",
		RetroachivementsApiKey:   "",
	})

	if err != nil {
		t.Error("Expected no error, got", err)
	}

	if dao.savedSettings.RetroAchievementsUsername == "" {
		t.Error("Expected saved credentials, got empty username")
	}

	if credentialsManager.items["retroachivementsPassword"] != "" {
		t.Error("Expected empty password")
	}

	if credentialsManager.items["retroachivementsApiKey"] != "" {
		t.Error("Expected empty api key")
	}
}

func Test_it_should_be_able_to_save_retroachivements_password(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{items: make(map[string]string)}
	dao := &settingsDAOMock{}
	sut := application.NewSaveSettings(dao, fs, credentialsManager)

	fs.SaveFile("/path/to/retroarch", []byte("data"))
	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath:      "/path/to/retroarch",
		RomsFolderPath:           "/path/to/roms",
		RetroachivementsUsername: "",
		RetroachivementsPassword: "password",
		RetroachivementsApiKey:   "",
	})

	if err != nil {
		t.Error("Expected no error, got", err)
	}

	if dao.savedSettings.RetroAchievementsUsername != "" {
		t.Error("Expected no username")
	}

	if credentialsManager.items["retroachivementsPassword"] == "" {
		t.Error("Expected saved credentials, got empty password")
	}

	if credentialsManager.items["retroachivementsApiKey"] != "" {
		t.Error("Expected empty api key")
	}
}

func Test_it_should_be_able_to_save_retroachivements_api_key(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{items: make(map[string]string)}
	dao := &settingsDAOMock{}
	sut := application.NewSaveSettings(dao, fs, credentialsManager)

	fs.SaveFile("/path/to/retroarch", []byte("data"))
	fs.SaveFile("/path/to/roms", []byte("data"))

	err := sut.Execute(application.SaveSettingsInput{
		RetroarchFolderPath:      "/path/to/retroarch",
		RomsFolderPath:           "/path/to/roms",
		RetroachivementsUsername: "",
		RetroachivementsPassword: "",
		RetroachivementsApiKey:   "apiKey",
	})

	if err != nil {
		t.Error("Expected no error, got", err)
	}

	if dao.savedSettings.RetroAchievementsUsername != "" {
		t.Error("Expected no username")
	}

	if credentialsManager.items["retroachivementsPassword"] != "" {
		t.Error("Expected no password")
	}

	if credentialsManager.items["retroachivementsApiKey"] == "" {
		t.Error("Expected saved credentials, got empty api key")
	}
}

func Test_it_should_be_able_to_return_error_given_no_retroarch_folder_was_found(t *testing.T) {
	fs := game_doubles_test.NewMockFileSystem()
	credentialsManager := &credentialsManagerMock{}
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs, credentialsManager)

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
	credentialsManager := &credentialsManagerMock{}
	sut := application.NewSaveSettings(&settingsDAOMock{}, fs, credentialsManager)

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

type credentialsManagerMock struct {
	items map[string]string
}

func (m *credentialsManagerMock) SaveCredentials(key, value string) error {
	m.items[key] = value
	return nil
}

func (m *credentialsManagerMock) GetCredentials(key string) (string, error) {
	return m.items[key], nil
}
