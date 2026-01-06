import { FilepathSelectionService } from "../../application/FilepathSelectionService";

export class NoOpFilepathSelectionService implements FilepathSelectionService {
    selectFile(): Promise<string> {
        return Promise.resolve("");
    }
}