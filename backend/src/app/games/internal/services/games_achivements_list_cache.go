package services

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/shared/cache"
)

type gamesAchievementsListCache struct {
	gamesAchievementsListCache map[string][]application.Achievement
}

func NewGamesAchievementsListCache() cache.Cache[string, []application.Achievement] {
	return &gamesAchievementsListCache{
		gamesAchievementsListCache: make(map[string][]application.Achievement),
	}
}

func (c *gamesAchievementsListCache) Set(gameId string, achievements []application.Achievement) {
	c.gamesAchievementsListCache[gameId] = achievements
}

func (c *gamesAchievementsListCache) Get(gameId string) []application.Achievement {
	return c.gamesAchievementsListCache[gameId]
}

func (c *gamesAchievementsListCache) Delete(gameId string) {
	delete(c.gamesAchievementsListCache, gameId)
}
