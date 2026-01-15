package get_platform_types

import "retrolauncher/backend/src/app/games/domain/platform"

type GetPlatformTypes struct {
	Execute func() *[]string
}

func New() *GetPlatformTypes {
	return &GetPlatformTypes{
		Execute: func() *[]string { return execute() },
	}
}

func execute() *[]string {
	return &[]string{platform.TypeRetroArch}
}
