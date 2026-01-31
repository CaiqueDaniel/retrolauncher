import { PropsWithChildren, useMemo } from "react";
import { FilepathSelectorContext } from "./infra/features/FilepathSelector/FilepathSelectorContext";
import { LocalFilepathSelectionService } from "./infra/services/LocalFilepathSelectionService";

export function SharedProviders({ children }: PropsWithChildren) {
  const filepathSelectionService = useMemo(
    () => new LocalFilepathSelectionService(),
    [],
  );

  return (
    <FilepathSelectorContext.Provider
      value={{
        filepathSelectionService,
      }}
    >
      {children}
    </FilepathSelectorContext.Provider>
  );
}
