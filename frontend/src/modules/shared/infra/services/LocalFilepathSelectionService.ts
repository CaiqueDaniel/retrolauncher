import { FilepathSelectionService } from "../../application/FilepathSelectionService";
import { SelectFile } from '~/../wailsjs/go/main/App'

export class LocalFilepathSelectionService implements FilepathSelectionService {
    selectFile(): Promise<string> {
        return SelectFile()
    }
}