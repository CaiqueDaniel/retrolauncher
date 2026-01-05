package create_platform

import platform "retrolauncher/backend/src/app/platform/domain"

type CreatePlatform struct {
	Execute func(input Input) []error
}

func New(factory platform.PlatformFactory, repository platform.PlatformRepository) *CreatePlatform {
	return &CreatePlatform{
		Execute: func(input Input) []error { return execute(input, factory, repository) },
	}
}

func execute(input Input, factory platform.PlatformFactory, repository platform.PlatformRepository) []error {
	platformEntity, validationErrors := factory.Create(input.Name, input.Path, platform.NewType(input.PlatformType))

	if len(validationErrors) > 0 {
		return validationErrors
	}

	repository.Save(platformEntity)
	return nil
}

type Input struct {
	Name         string
	Path         string
	PlatformType string
}
