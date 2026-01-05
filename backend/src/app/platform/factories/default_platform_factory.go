package platform_factories

import platform "retrolauncher/backend/src/app/platform/domain"

type DefaultPlatformFactory struct{}

func (f *DefaultPlatformFactory) Create(name, path string, platformType *platform.Type) (*platform.Platform, []error) {
	return platform.NewPlatform(name, path, platformType)
}
