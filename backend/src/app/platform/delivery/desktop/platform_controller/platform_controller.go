package platform_controller

import (
	"retrolauncher/backend/src/app/platform/application/create_platform"
	"retrolauncher/backend/src/app/platform/application/get_platform_types"
	list_platforms "retrolauncher/backend/src/app/platform/application/list-platforms"
)

type PlatformController struct {
	createPlatform   *create_platform.CreatePlatform
	getPlatformTypes *get_platform_types.GetPlatformTypes
	listPlatforms    *list_platforms.ListPlatforms
}

func New(
	createPlatform *create_platform.CreatePlatform,
	getPlatformTypes *get_platform_types.GetPlatformTypes,
	listPlatforms *list_platforms.ListPlatforms,
) *PlatformController {
	return &PlatformController{
		createPlatform:   createPlatform,
		getPlatformTypes: getPlatformTypes,
		listPlatforms:    listPlatforms,
	}
}

func (c *PlatformController) Create(input CreateInputDto) []error {
	return c.createPlatform.Execute(create_platform.Input{
		Name:         input.Name,
		PlatformType: input.PlatformType,
		Path:         input.Path,
	})
}

func (c *PlatformController) GetPlatformTypes() []string {
	return *c.getPlatformTypes.Execute()
}

func (c *PlatformController) List() ([]*list_platforms.Output, error) {
	return c.listPlatforms.Execute()
}

type CreateInputDto struct {
	Name         string `json:"name"`
	PlatformType string `json:"type"`
	Path         string `json:"path"`
}
