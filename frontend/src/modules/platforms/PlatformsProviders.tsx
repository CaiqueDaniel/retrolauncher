import { toast } from "react-toastify";
import { PlatformFormContext } from "./features/PlatformForm/PlatformFormContext";
import { PlatformRepository } from "./domain/Platform";
import { PropsWithChildren, useMemo } from "react";
import { FilepathSelectorContext } from "../shared/infra/features/FilepathSelector/FilepathSelectorContext";
import { LocalFilepathSelectionService } from "../shared/infra/services/LocalFilepathSelectionService";
import { useReactRouterRouteNavigator } from "../shared/infra/hooks/useReactRouterRouteNavigator";

export function PlatformsProviders({ children }: PropsWithChildren) {
    const filepathSelectionService = useMemo(() => new LocalFilepathSelectionService(), [])

    return (
        <FilepathSelectorContext.Provider value={{ filepathSelectionService }}>
            <PlatformFormContext.Provider value={{
                repository: {} as PlatformRepository,
                alert: toast,
                routeNavigator: useReactRouterRouteNavigator()
            }}>
                {children}
            </PlatformFormContext.Provider>
        </FilepathSelectorContext.Provider>
    )
}