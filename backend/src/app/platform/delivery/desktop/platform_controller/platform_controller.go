package platform_controller

import (
	"retrolauncher/backend/src/app/platform/application/create_platform"
)

type PlatformController struct {
	createPlatform *create_platform.CreatePlatform
}

func New(
	createPlatform *create_platform.CreatePlatform,
) *PlatformController {
	return &PlatformController{
		createPlatform: createPlatform,
	}
}

func (c *PlatformController) Create(input CreateInputDto) []error {
	return c.createPlatform.Execute(create_platform.Input{
		Name:         input.Name,
		PlatformType: input.PlatformType,
		Path:         input.Path,
	})
}

type CreateInputDto struct {
	Name         string `json:"name"`
	PlatformType string `json:"type"`
	Path         string `json:"path"`
}
