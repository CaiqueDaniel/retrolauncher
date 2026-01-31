import { PathSelectionService } from "../../application/FilepathSelectionService";
import { SelectFolder } from '~/../wailsjs/go/main/App'

export class LocalFilepathSelectionService implements PathSelectionService {
    selectPath(): Promise<string> {
        return SelectFolder();
    }
}