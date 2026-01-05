package domain

type PlatformFactory interface {
	Create(Name, Path string, platformType *Type) (*Platform, []error)
}
