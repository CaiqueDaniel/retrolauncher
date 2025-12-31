package platform

type PlatformFactory interface {
	Create(Name, Path string) (*Platform, []error)
}
