import { toast } from "react-toastify";
import { PlatformFormContext } from "./features/PlatformForm/PlatformFormContext";
import { PlatformRepository } from "./domain/Platform";
import { PropsWithChildren } from "react";

export function PlatformsProviders({ children }: PropsWithChildren) {
    return (
        <PlatformFormContext.Provider value={{ repository: {} as PlatformRepository, alert: toast }}>
            {children}
        </PlatformFormContext.Provider>
    )
}