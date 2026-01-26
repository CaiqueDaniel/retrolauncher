import { FilepathSelectionService } from "../../application/FilepathSelectionService";
import { SelectFile } from '~/../wailsjs/go/main/App'

export class LocalFilepathSelectionService implements FilepathSelectionService {
    selectFile(extensions: string[]): Promise<string> {
        return SelectFile(extensions.join(";"));
    }
}