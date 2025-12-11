package application_game

import "retrolauncher/backend/internal/app/games/domain/game"

func ListGames(input ListGamesInput, repository game.GameRepository) []ListGamesOutput {
	games := repository.List(game.ListGamesParams{
		Name: input.Name,
	})

	var result []ListGamesOutput
	for _, game := range games {
		result = append(result, ListGamesOutput{
			Id:       game.GetId().String(),
			Name:     game.GetName(),
			Platform: game.GetPlatform(),
			Path:     game.GetPath(),
			Cover:    game.GetCover(),
		})
	}

	return result
}

type ListGamesInput struct {
	Name string
}

type ListGamesOutput struct {
	Id       string
	Name     string
	Platform string
	Path     string
	Cover    string
}
