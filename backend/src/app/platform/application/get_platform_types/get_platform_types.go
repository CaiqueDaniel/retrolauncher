package get_platform_types

import "retrolauncher/backend/src/app/platform/domain"

type GetPlatformTypes struct {
	Execute func() *[]string
}

func New() *GetPlatformTypes {
	return &GetPlatformTypes{
		Execute: func() *[]string { return execute() },
	}
}

func execute() *[]string {
	return &[]string{domain.TypeRetroArch}
}
