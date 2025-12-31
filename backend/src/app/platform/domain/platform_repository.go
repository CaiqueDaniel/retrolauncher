package platform

type PlatformRepository interface {
	Save(platform *Platform) error
}
