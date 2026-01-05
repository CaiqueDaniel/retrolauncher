package memory_platform_repository

import platform "retrolauncher/backend/src/app/platform/domain"

type MemoryPlatformRepository struct {
	platforms map[string]*platform.Platform
}

func (r *MemoryPlatformRepository) Save(entity *platform.Platform) error {
	if r.platforms == nil {
		r.platforms = make(map[string]*platform.Platform)
	}
	r.platforms[entity.GetID().String()] = entity
	return nil
}

func (r *MemoryPlatformRepository) Size() int {
	if r.platforms == nil {
		return 0
	}
	return len(r.platforms)
}
