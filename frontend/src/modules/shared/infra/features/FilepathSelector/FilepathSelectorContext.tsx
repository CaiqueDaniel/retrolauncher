import { createContext, PropsWithChildren } from "react";
import { FilepathSelectionService } from "~/modules/shared/application/FilepathSelectionService";

export const FilepathSelectorContext = createContext<Context | undefined>(undefined);

export function FilepathSelectorProvider({ children }: PropsWithChildren) {
    return (
        <FilepathSelectorContext.Provider value={undefined}>
            {children}
        </FilepathSelectorContext.Provider>
    );
}

type Context = {
    filepathSelectionService: FilepathSelectionService
}