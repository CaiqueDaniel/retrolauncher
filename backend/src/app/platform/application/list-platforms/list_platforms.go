package list_platforms

import "retrolauncher/backend/src/app/platform/domain"

type ListPlatforms struct {
	Execute func() ([]*Output, error)
}

func New(repository domain.PlatformRepository) *ListPlatforms {
	return &ListPlatforms{
		Execute: func() ([]*Output, error) {
			return execute(repository)
		},
	}
}

func execute(repository domain.PlatformRepository) ([]*Output, error) {
	platforms, err := repository.List()

	if err != nil {
		return make([]*Output, 0), err
	}

	result := make([]*Output, len(platforms))

	for i, platform := range platforms {
		result[i] = &Output{
			Id:   platform.GetID().String(),
			Name: platform.GetName(),
		}
	}

	return result, nil
}

type Output struct {
	Id   string
	Name string
}
