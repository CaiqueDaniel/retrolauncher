import { PlatformTypesService } from "../services/PlatformTypesService";
import { GetPlatformTypes } from '~/../wailsjs/go/platform_controller/PlatformController'

export class LocalPlatformGateway implements PlatformTypesService {
    getPlatformTypes(): Promise<string[]> {
        return GetPlatformTypes();
    }
}