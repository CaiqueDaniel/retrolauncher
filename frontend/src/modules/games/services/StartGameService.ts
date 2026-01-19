export interface StartGameService {
    startGame(gameId: string): Promise<void>;
}