export type Platform = {
    id?: string;
    name: string;
    type: string;
    path: string;
}

export interface PlatformRepository {
    save(platform: Platform): Promise<void>;
}