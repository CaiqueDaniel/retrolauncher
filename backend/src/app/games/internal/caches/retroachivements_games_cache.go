package caches

type RetroAchievementsGamesCache interface {
	GetCachedGameRelation() map[string]string
}
