import { createContext } from "react";
import { FilepathSelectionService } from "~/modules/shared/application/FilepathSelectionService";
import { useContextHandler } from "../../hooks/useContextHandler";

export const FilepathSelectorContext = createContext<Context | undefined>(undefined);

export function useFilepathSelectorContext() {
    return useContextHandler(FilepathSelectorContext);
}

type Context = {
    filepathSelectionService: FilepathSelectionService
}