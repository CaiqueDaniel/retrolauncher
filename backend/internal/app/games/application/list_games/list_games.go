package list_games

import "retrolauncher/backend/internal/app/games/domain/game"

type ListGames struct {
	Execute func(input Input) []Output
}

func New(repository game.GameRepository) *ListGames {
	return &ListGames{
		Execute: func(input Input) []Output { return execute(input, repository) },
	}
}

func execute(input Input, repository game.GameRepository) []Output {
	games := repository.List(game.ListGamesParams{
		Name: input.Name,
	})

	var result []Output
	for _, game := range games {
		result = append(result, Output{
			Id:       game.GetId().String(),
			Name:     game.GetName(),
			Platform: game.GetPlatform(),
			Path:     game.GetPath(),
			Cover:    game.GetCover(),
		})
	}

	return result
}

type Input struct {
	Name string
}

type Output struct {
	Id       string
	Name     string
	Platform string
	Path     string
	Cover    string
}
