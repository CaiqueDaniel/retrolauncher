package application

type ShortcutService interface {
	CreateDesktopShortcut(gameId, gameName, gameCover string) error
}
