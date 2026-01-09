package domain

type PlatformRepository interface {
	Save(platform *Platform) error
	List() ([]*Platform, error)
}
