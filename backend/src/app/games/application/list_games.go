package application

import (
	"fmt"
	"retrolauncher/backend/src/app/games/domain"
	"retrolauncher/backend/src/shared/application"
)

type ListGames interface {
	Execute(input ListGamesInput) []*ListGamesOutput
}

type listGames struct {
	repository    domain.GameRepository
	imageUploader application.ImageUploader
}

func NewListGames(repository domain.GameRepository, imageUploader application.ImageUploader) ListGames {
	return &listGames{
		repository:    repository,
		imageUploader: imageUploader,
	}
}

func (l *listGames) Execute(input ListGamesInput) []*ListGamesOutput {
	games := l.repository.List(domain.ListGamesParams{
		Name: input.Name,
	})

	result := make([]*ListGamesOutput, 0)

	for _, game := range games {
		cover, err := l.imageUploader.OpenAsBase64(game.GetCover())

		fmt.Println(game)
		fmt.Println(err)

		result = append(result, &ListGamesOutput{
			Id:           game.GetId().String(),
			Name:         game.GetName(),
			PlatformType: game.GetPlatformType().GetPlatformType(),
			PlatformPath: game.GetPlatformType().GetPath(),
			Path:         game.GetPath(),
			Cover:        cover,
		})
	}

	return result
}

type ListGamesInput struct {
	Name string
}

type ListGamesOutput struct {
	Id           string
	Name         string
	PlatformType string
	PlatformPath string
	Path         string
	Cover        string
}
