package platform_application

import platform "retrolauncher/backend/internal/app/platform/domain"

func CreatePlatform(input CreatePlatformInput, factory platform.PlatformFactory, repository platform.PlatformRepository) []error {
	platformEntity, validationErrors := factory.Create(input.Name, input.Path)

	if len(validationErrors) > 0 {
		return validationErrors
	}

	repository.Save(platformEntity)
	return nil
}

type CreatePlatformInput struct {
	Name string
	Path string
}
