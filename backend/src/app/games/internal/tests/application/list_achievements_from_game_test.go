package application_game_test

import (
	"errors"
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/domain/game"
	"retrolauncher/backend/src/app/games/internal/domain/platform"
	game_doubles_test "retrolauncher/backend/src/app/games/internal/tests/doubles"
	"testing"
)

// --- doubles ---

type mockRetroAchievementsService struct {
	achievements []application.Achievement
	err          error
	calledHash   string
	calledUser   string
	calledKey    string
}

func (m *mockRetroAchievementsService) GetAchivementsByHash(hash, username, apiKey string) ([]application.Achievement, error) {
	m.calledHash = hash
	m.calledUser = username
	m.calledKey = apiKey
	return m.achievements, m.err
}

// --- helpers ---

func defaultSettings() *application.Settings {
	return &application.Settings{
		RetroAchievementsUsername: "user123",
		RetroAchievementsApiKey:   "key-abc",
	}
}

func defaultGame(t *testing.T) *game.Game {
	t.Helper()
	g, errs := game.New(
		"Sonic",
		platform.New(platform.TypeRetroArch, "/cores"),
		"/roms/sonic.bin",
		"",
		"abc123hash",
	)
	if len(errs) > 0 {
		t.Fatalf("failed to create test game: %v", errs)
	}
	return g
}

// --- tests ---

func Test_ListAchievementsFromGame_returns_achievements_on_success(t *testing.T) {
	repo := &game_doubles_test.MemoryGameRepository{}
	g := defaultGame(t)
	repo.Save(g)

	expectedAchievements := []application.Achievement{
		{ID: 1, Title: "First Blood", Description: "Win first match", Points: 10},
		{ID: 2, Title: "Speed Run", Description: "Finish in under 5 min", Points: 25, DateEarned: "2024-01-01"},
	}

	settingsSvc := &mockSettingsService{settings: defaultSettings()}
	retroSvc := &mockRetroAchievementsService{achievements: expectedAchievements}

	sut := application.NewListAchievementsFromGame(repo, retroSvc, settingsSvc)
	result, err := sut.Execute(application.ListAchievementsFromGameInput{Id: g.GetId().String()})

	if err != nil {
		t.Fatalf("expected no error, got: %v", err)
	}

	if len(result) != len(expectedAchievements) {
		t.Fatalf("expected %d achievements, got %d", len(expectedAchievements), len(result))
	}

	if result[0].ID != expectedAchievements[0].ID {
		t.Errorf("expected first achievement ID %d, got %d", expectedAchievements[0].ID, result[0].ID)
	}

	if retroSvc.calledHash != g.GetHash() {
		t.Errorf("expected hash %q to be passed, got %q", g.GetHash(), retroSvc.calledHash)
	}

	if retroSvc.calledUser != defaultSettings().RetroAchievementsUsername {
		t.Errorf("expected username %q, got %q", defaultSettings().RetroAchievementsUsername, retroSvc.calledUser)
	}

	if retroSvc.calledKey != defaultSettings().RetroAchievementsApiKey {
		t.Errorf("expected api key %q, got %q", defaultSettings().RetroAchievementsApiKey, retroSvc.calledKey)
	}
}

func Test_ListAchievementsFromGame_returns_error_when_settings_fail(t *testing.T) {
	repo := &game_doubles_test.MemoryGameRepository{}
	settingsErr := errors.New("settings unavailable")

	settingsSvc := &mockSettingsService{err: settingsErr}
	retroSvc := &mockRetroAchievementsService{}

	sut := application.NewListAchievementsFromGame(repo, retroSvc, settingsSvc)
	result, err := sut.Execute(application.ListAchievementsFromGameInput{Id: "any-id"})

	if err == nil {
		t.Fatal("expected an error, got nil")
	}

	if err != settingsErr {
		t.Errorf("expected error %v, got %v", settingsErr, err)
	}

	if result != nil {
		t.Errorf("expected nil result, got %v", result)
	}

	if retroSvc.calledHash != "" {
		t.Error("expected RetroAchievementsService NOT to be called when settings fail")
	}
}

func Test_ListAchievementsFromGame_returns_error_when_game_not_found(t *testing.T) {
	repo := &game_doubles_test.MemoryGameRepository{}
	settingsSvc := &mockSettingsService{settings: defaultSettings()}
	retroSvc := &mockRetroAchievementsService{}

	sut := application.NewListAchievementsFromGame(repo, retroSvc, settingsSvc)
	result, err := sut.Execute(application.ListAchievementsFromGameInput{Id: "nonexistent-id"})

	if err == nil {
		t.Fatal("expected an error when game is not found, got nil")
	}

	if result != nil {
		t.Errorf("expected nil result, got %v", result)
	}

	if retroSvc.calledHash != "" {
		t.Error("expected RetroAchievementsService NOT to be called when game is not found")
	}
}

func Test_ListAchievementsFromGame_returns_error_when_retroachievements_service_fails(t *testing.T) {
	repo := &game_doubles_test.MemoryGameRepository{}
	g := defaultGame(t)
	repo.Save(g)

	retroErr := errors.New("external service error")
	settingsSvc := &mockSettingsService{settings: defaultSettings()}
	retroSvc := &mockRetroAchievementsService{err: retroErr}

	sut := application.NewListAchievementsFromGame(repo, retroSvc, settingsSvc)
	result, err := sut.Execute(application.ListAchievementsFromGameInput{Id: g.GetId().String()})

	if err == nil {
		t.Fatal("expected an error, got nil")
	}

	if err != retroErr {
		t.Errorf("expected error %v, got %v", retroErr, err)
	}

	if result != nil {
		t.Errorf("expected nil result, got %v", result)
	}
}
