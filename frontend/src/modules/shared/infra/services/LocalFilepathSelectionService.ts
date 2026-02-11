import { PathSelectionService } from "../../application/FilepathSelectionService";
import { SelectFile } from '~/../wailsjs/go/main/App'

export class LocalFilepathSelectionService implements PathSelectionService {
    selectPath(extensions: string[]): Promise<string> {
        return SelectFile(extensions.join(";"));
    }
}