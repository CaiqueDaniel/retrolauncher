export interface PlatformSearchService {
    listAll(): Promise<PlatformSearchResult[]>
}

export type PlatformSearchResult = {
    id: string;
    name: string;
}