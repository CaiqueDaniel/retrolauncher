import { Game, GameRepository } from "../domain/Game";
import { Create, Update, List, Get, GetPlatformTypes, StartGame } from "~/../wailsjs/go/game_controller/GameController";
import { GameQueryRepository } from "../repositories/GameQueryRepository";
import { PlatformTypesService } from "../services/PlatformTypesService";
import { StartGameService } from "../services/StartGameService";

export class LocalGameGateway implements
    GameRepository,
    GameQueryRepository.Repository,
    PlatformTypesService,
    StartGameService {
    async save(game: Game): Promise<void> {
        if (game.id) {
            await Update({ ...game, id: game.id });
            return;
        }
        await Create(game);
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
}