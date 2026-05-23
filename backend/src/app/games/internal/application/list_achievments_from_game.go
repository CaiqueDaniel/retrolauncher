package application

import (
	"errors"

	"retrolauncher/backend/src/app/games/internal/domain"
)

type ListAchievementsFromGame interface {
	Execute(input ListAchievementsFromGameInput) ([]Achievement, error)
}

type listAchievementsFromGame struct {
	repository               domain.GameRepository
	retroAchievementsService RetroAchievementsService
	settingsService          SettingsService
}

func NewListAchievementsFromGame(
	repository domain.GameRepository,
	retroAchievementsService RetroAchievementsService,
	settingsService SettingsService,
) ListAchievementsFromGame {
	return &listAchievementsFromGame{
		repository:               repository,
		retroAchievementsService: retroAchievementsService,
		settingsService:          settingsService,
	}
}

func (l *listAchievementsFromGame) Execute(input ListAchievementsFromGameInput) ([]Achievement, error) {
	settings, err := l.settingsService.GetSettings()

	if err != nil {
		return nil, err
	}

	game, err := l.repository.Get(input.Id)

	if err != nil {
		return nil, err
	}

	if game == nil {
		return nil, errors.New("game not found")
	}

	achievements, err := l.retroAchievementsService.GetAchivementsByHash(
		game.GetHash(),
		settings.RetroAchievementsUsername,
		settings.RetroAchievementsApiKey,
	)

	if err != nil {
		return nil, err
	}

	return achievements, nil
}

type ListAchievementsFromGameInput struct {
	Id string
}
