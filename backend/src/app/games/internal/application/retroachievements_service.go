package application

type RetroAchievementsService interface {
	GetAchivementsByHash(hash string, username string, apiKey string) ([]Achievement, error)
}

type Achievement struct {
	ID          int    `json:"ID"`
	Title       string `json:"Title"`
	Description string `json:"Description"`
	Points      int    `json:"Points"`
	DateEarned  string `json:"DateEarned,omitempty"`
}
