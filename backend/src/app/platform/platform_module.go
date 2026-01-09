package platform

import (
	"retrolauncher/backend/src/app/platform/application/create_platform"
	"retrolauncher/backend/src/app/platform/application/get_platform_types"
	list_platforms "retrolauncher/backend/src/app/platform/application/list-platforms"
	"retrolauncher/backend/src/app/platform/delivery/desktop/platform_controller"
	platform_factories "retrolauncher/backend/src/app/platform/factories"
	memory_platform_repository "retrolauncher/backend/src/app/platform/persistance/memory_platform_repository"
)

type PlatformModule struct {
	PlatformController *platform_controller.PlatformController
}

func NewPlatformModule() *PlatformModule {
	platformFactory := &platform_factories.DefaultPlatformFactory{}
	platformRepository := &memory_platform_repository.MemoryPlatformRepository{}

	return &PlatformModule{
		PlatformController: platform_controller.New(
			create_platform.New(platformFactory, platformRepository),
			get_platform_types.New(),
			list_platforms.New(platformRepository),
		),
	}
}
