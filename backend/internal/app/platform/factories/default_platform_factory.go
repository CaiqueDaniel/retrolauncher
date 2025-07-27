package platform_factories

import platform "retrolauncher/backend/internal/app/platform/domain"

type DefaultPlatformFactory struct{}

func (f *DefaultPlatformFactory) Create(name, path string) (*platform.Platform, []error) {
	return platform.New(name, path)
}
