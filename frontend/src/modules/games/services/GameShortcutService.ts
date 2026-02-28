export interface GameShortcutService {
    createDesktopShortcut(gameId: string): Promise<void>;
}