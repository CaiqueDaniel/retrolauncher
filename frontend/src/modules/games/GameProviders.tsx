import { PropsWithChildren, useMemo } from "react";
import { GameFormContext } from "./features/GameForm/GameFormContext";
import { LocalGameGateway } from "./gateways/LocalGameGateway";
import { toast } from "react-toastify";
import { GameListContext } from "./features/GameList/GameListContext";

export function GameProviders({ children }: PropsWithChildren) {
    const repository = useMemo(() => new LocalGameGateway(), [])

    return (
        <GameListContext.Provider value={{ alert: toast, queryRepository: repository }}>
            <GameFormContext.Provider value={{ repository, alert: toast }}>
                {children}
            </GameFormContext.Provider>
        </GameListContext.Provider>
    )
}