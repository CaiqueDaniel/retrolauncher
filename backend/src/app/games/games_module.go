package games

import (
	"retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/list_games"
	"retrolauncher/backend/src/app/games/application/update_game"
	"retrolauncher/backend/src/app/games/delivery/desktop/game_controller"
	game_factories "retrolauncher/backend/src/app/games/factories"
	"retrolauncher/backend/src/app/games/persistance/memory_game_repository"
)

type GamesModule struct {
	GameController *game_controller.GameController
}

func NewGamesModule() *GamesModule {
	gameFactory := &game_factories.DefaultGameFactory{}
	gameRepository := &memory_game_repository.MemoryGameRepository{}

	return &GamesModule{
		GameController: game_controller.New(
			create_game.New(gameFactory, gameRepository),
			update_game.New(gameRepository),
			//get_game.New(),
			list_games.New(gameRepository),
		),
	}
}
