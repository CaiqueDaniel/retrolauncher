package services

import (
	"encoding/json"
	"fmt"
	"net/http"
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/app/games/internal/caches"
)

type httpRetroAchievementsGateway struct {
	httpClient *http.Client
	baseURL    string
	cache      caches.RetroAchievementsGamesCache
}

func NewHTTPRetroAchievementsGateway(
	httpClient *http.Client,
	baseURL string,
	cache caches.RetroAchievementsGamesCache,
) application.RetroAchievementsService {
	return &httpRetroAchievementsGateway{
		httpClient: httpClient,
		baseURL:    baseURL,
		cache:      cache,
	}
}

func (g *httpRetroAchievementsGateway) GetAchivementsByHash(gameHash, username, apiKey string) ([]application.Achievement, error) {
	gameId := g.cache.GetCachedGameRelation()[gameHash]

	if gameId == "" {
		return nil, fmt.Errorf("game not found in cache with hash: %s", gameHash)
	}

	url := fmt.Sprintf("%s/API/API_GetGameInfoAndUserProgress.php?u=%s&y=%s&g=%s",
		g.baseURL, username, apiKey, gameId)

	req, err := http.NewRequest("GET", url, nil)

	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Accept", "application/json")
	req.Header.Add("Accept-Language", "pt-BR,pt;q=0.9,en;q=0.8")
	req.Header.Add("Accept-Charset", "utf-8")
	req.Header.Add("User-Agent", "RetroLauncher/1.0")

	if err != nil {
		return nil, fmt.Errorf("error creating request: %w", err)
	}

	resp, err := g.httpClient.Do(req)
	if err != nil {
		return nil, fmt.Errorf("error making request to API: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("API returned status %d: %s", resp.StatusCode, resp.Status)
	}

	var achievementsResponse AchievementsResponse
	if err := json.NewDecoder(resp.Body).Decode(&achievementsResponse); err != nil {
		return nil, fmt.Errorf("error decoding JSON response: %w", err)
	}

	var achievements []application.Achievement
	for _, achievement := range achievementsResponse.Achievements {
		achievements = append(achievements, achievement)
	}

	return achievements, nil
}

type AchievementsResponse struct {
	Achievements map[string]application.Achievement `json:"Achievements"`
}
