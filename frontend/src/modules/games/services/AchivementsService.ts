export interface AchievementsService {
    listAchievementsFromGame(gameId: string): Promise<Achievement[]>
}

export interface Achievement {
    id: number
    title: string
    description: string
    points: number
    dateEarned?: string
}