export interface GameRepository {
    save(game: Game): Promise<void>
    get(id: string): Promise<Game>
}

export type Game = {
    id?: string
    name: string
    platform: string
    path: string
    cover: string
}