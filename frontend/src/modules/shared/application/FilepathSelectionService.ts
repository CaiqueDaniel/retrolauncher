export interface FilepathSelectionService {
    selectFile(extensions: string[]): Promise<string>;
}