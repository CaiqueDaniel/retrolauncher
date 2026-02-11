import { PathSelectionService } from "../../application/FilepathSelectionService";
import { SelectFolder } from '~/../wailsjs/go/main/App'

export class LocalFolderPathSelectionService implements PathSelectionService {
    selectPath(): Promise<string> {
        return SelectFolder();
    }
}