import { PathSelectionService } from "../../application/FilepathSelectionService";

export class NoOpFilepathSelectionService implements PathSelectionService {
    selectPath(): Promise<string> {
        return Promise.resolve("");
    }
}