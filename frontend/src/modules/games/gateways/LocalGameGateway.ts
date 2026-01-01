import { Game, GameRepository } from "../domain/Game";
import { Create, Update, List } from "~/../wailsjs/go/game_controller/GameController";
import { GameQueryRepository } from "../repositories/GameQueryRepository";

export class LocalGameGateway implements GameRepository, GameQueryRepository.Repository {
    save(game: Game): Promise<void> {
        return game.id ? Update({ ...game, id: game.id }) : Create(game)
    }

    get(id: string): Promise<Game> {
        throw new Error("Method not implemented.");
    }

    async search(params: GameQueryRepository.Params): Promise<GameQueryRepository.Output[]> {
        const result = await List({ name: params.name || "" })
        return result.map((game) => ({
            id: game.Id,
            name: game.Name,
            platform: game.Platform,
            cover: game.Cover,
        }))
    }
}