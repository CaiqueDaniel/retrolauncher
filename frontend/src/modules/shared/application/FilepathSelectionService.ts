export interface FilepathSelectionService {
    selectFile(): Promise<string>;
}