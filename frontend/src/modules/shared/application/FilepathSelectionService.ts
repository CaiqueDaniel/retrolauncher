export interface PathSelectionService {
    selectPath(extensions: string[]): Promise<string>;
}