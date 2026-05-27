package services

import (
	"encoding/json"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/caches"
	shared_application "retrolauncher/backend/src/shared/application"
	"strconv"
	"strings"
)

type localRetroAchievementsGamesCache struct {
	gameRelation map[string]string
	fs           shared_application.FileSystem
	basePath     string
}

func NewLocalRetroAchievementsGamesCache(fs shared_application.FileSystem, basePath string) caches.RetroAchievementsGamesCache {
	return &localRetroAchievementsGamesCache{
		gameRelation: make(map[string]string),
		fs:           fs,
		basePath:     basePath,
	}
}

func (c *localRetroAchievementsGamesCache) GetCachedGameRelation() map[string]string {
	if len(c.gameRelation) == 0 {
		c.loadDataFromFilesIntoCache()
	}

	return c.gameRelation
}

func (c *localRetroAchievementsGamesCache) loadDataFromFilesIntoCache() {
	const folderPath = "resources/retroachievements"
	dir := filepath.Join(c.basePath, folderPath)
	files, err := c.fs.ListFiles(dir)

	if err != nil {
		return
	}

	for _, file := range files {
		if strings.HasSuffix(file, ".json") {
			data, err := c.fs.ReadFile(file)

			if err != nil {
				continue
			}

			c.parseDataAndAttribute(data)
		}
	}
}

func (c *localRetroAchievementsGamesCache) parseDataAndAttribute(data []byte) {
	var itemsFromFile []itemFromFile
	json.Unmarshal(data, &itemsFromFile)

	for _, item := range itemsFromFile {
		for _, hash := range item.Hashes {
			c.gameRelation[hash] = strconv.Itoa(item.ID)
		}
	}
}

type itemFromFile struct {
	ID     int      `json:"ID"`
	Hashes []string `json:"Hashes"`
}
