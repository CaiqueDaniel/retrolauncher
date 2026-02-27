package application

import (
	"fmt"
	"retrolauncher/backend/src/app/games/internal/domain"
	"retrolauncher/backend/src/shared/application"
)

type CreateShortcut interface {
	Execute(gameId string) error
}

type createShortcut struct {
	repository      domain.GameRepository
	shortcutService ShortcutService
}

func NewCreateShortcut(repository domain.GameRepository, shortcutService ShortcutService) CreateShortcut {
	return &createShortcut{repository: repository, shortcutService: shortcutService}
}

func (c *createShortcut) Execute(gameId string) error {
	game, err := c.repository.Get(gameId)

	if err != nil {
		return err
	}

	if game == nil {
		return application.NotFoundError(fmt.Sprintf("game %s not found", gameId))
	}

	return c.shortcutService.CreateDesktopShortcut(gameId)
}
