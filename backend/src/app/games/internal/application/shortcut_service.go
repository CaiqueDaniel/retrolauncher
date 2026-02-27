package application

type ShortcutService interface {
	CreateDesktopShortcut(gameId string) error
}
