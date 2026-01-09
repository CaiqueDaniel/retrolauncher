import { Platform, PlatformRepository } from "../domain/Platform";
import { PlatformTypesService } from "../services/PlatformTypesService";
import { GetPlatformTypes, Create } from '~/../wailsjs/go/platform_controller/PlatformController'

export class LocalPlatformGateway implements PlatformRepository, PlatformTypesService {
    async save(platform: Platform): Promise<void> {
        await Create(platform);
    }

    getPlatformTypes(): Promise<string[]> {
        return GetPlatformTypes();
    }
}