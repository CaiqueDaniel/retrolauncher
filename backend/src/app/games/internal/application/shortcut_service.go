package application

type ShortcutService interface {
	CreateDesktopShortcut(gameId, gameName string) error
}
