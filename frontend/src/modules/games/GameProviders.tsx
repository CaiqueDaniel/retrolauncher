import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";

export function GameProviders({ children }: PropsWithChildren) {
    const repository = useMemo(() => new LocalGameGateway(), [])

    return (
        <GameFormContext.Provider value={{ repository, alert: toast }}>
            {children}
        </GameFormContext.Provider>
    )
}