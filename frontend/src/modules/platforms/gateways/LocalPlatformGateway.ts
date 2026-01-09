import { Platform, PlatformRepository } from "../domain/Platform";
import { PlatformSearchResult, PlatformSearchService } from "../services/PlatformSearchService";
import { PlatformTypesService } from "../services/PlatformTypesService";
import { GetPlatformTypes, Create, List } from '~/../wailsjs/go/platform_controller/PlatformController'

export class LocalPlatformGateway implements PlatformRepository, PlatformTypesService, PlatformSearchService {
    async save(platform: Platform): Promise<void> {
        await Create(platform);
    }

    getPlatformTypes(): Promise<string[]> {
        return GetPlatformTypes();
    }

    async listAll(): Promise<PlatformSearchResult[]> {
        const platforms = await List();
        return platforms.map(platform => ({
            id: platform.Id,
            name: platform.Name,
        }));
    }
}