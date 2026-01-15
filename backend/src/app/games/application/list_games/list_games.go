package list_games

import "retrolauncher/backend/src/app/games/domain"

type ListGames struct {
	Execute func(input Input) []*Output
}

func New(repository domain.GameRepository) *ListGames {
	return &ListGames{
		Execute: func(input Input) []*Output { return execute(input, repository) },
	}
}

func execute(input Input, repository domain.GameRepository) []*Output {
	games := repository.List(domain.ListGamesParams{
		Name: input.Name,
	})

	result := make([]*Output, 0)

	for _, game := range games {
		result = append(result, &Output{
			Id:       game.GetId().String(),
			Name:     game.GetName(),
			Platform: game.GetPlatformType().GetPlatformType(),
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
