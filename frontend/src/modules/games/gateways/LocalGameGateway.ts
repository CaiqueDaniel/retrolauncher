import { Game, GameRepository } from "../domain/Game";
import { Create, Update, List, Get, GetPlatformTypes, StartGame, AutoIndexGames, CreateDesktopShortcut, GetAchievements } from "~/../wailsjs/go/desktop/GameController";
import { GameQueryRepository } from "../repositories/GameQueryRepository";
import { PlatformTypesService } from "../services/PlatformTypesService";
import { StartGameService } from "../services/StartGameService";
import { IndexGamesService } from "../services/IndexGamesService";
import { GameShortcutService } from "../services/GameShortcutService";
import { Achievement, AchievementsService } from "../services/AchivementsService";

export class LocalGameGateway implements
    GameRepository,
    GameQueryRepository.Repository,
    PlatformTypesService,
    StartGameService,
    IndexGamesService,
    GameShortcutService,
    AchievementsService {
    async save(game: Game): Promise<void> {
        if (game.id) {
            const errors = await Update({ ...game, id: game.id });
            if (errors?.length > 0)
                throw new Error(errors.join(", "));
            return;
        }

        const errors = await Create(game);

        if (errors?.length > 0) {
            throw new Error(errors.join(", "));
        }
    }

    async get(id: string): Promise<Game> {
        const result = await Get({ id })
        return {
            id: result.Id,
            name: result.Name,
            platformType: result.PlatformType,
            platformPath: result.PlatformPath,
            cover: result.Cover,
            path: result.Path
        }
    }

    async search(params: GameQueryRepository.Params): Promise<GameQueryRepository.Output[]> {
        const result = await List({ name: params.name || "" })
        return result.map((game) => ({
            id: game.Id,
            name: game.Name,
            platform: game.PlatformType,
            platformPath: game.PlatformPath,
            cover: game.Cover,
        }))
    }

    getPlatformTypes(): Promise<string[]> {
        return GetPlatformTypes();
    }

    startGame(gameId: string): Promise<void> {
        return StartGame({ id: gameId })
    }

    indexGames(): Promise<void> {
        return AutoIndexGames()
    }

    createDesktopShortcut(gameId: string): Promise<void> {
        return CreateDesktopShortcut({ id: gameId })
    }

    async listAchievementsFromGame(gameId: string): Promise<Achievement[]> {
        const result = await GetAchievements({ id: gameId })
        return result.map((achievement) => ({
            id: achievement.ID,
            title: achievement.Title,
            description: achievement.Description,
            points: achievement.Points,
            dateEarned: achievement.DateEarned
        }))
    }
}