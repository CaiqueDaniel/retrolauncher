import { useBusSubscriber } from "~/modules/shared/infra/hooks/useBusSubscriber";
import { useGameViewerContext } from "./GameViewerContext";
import { GameEvents } from "../../GameEvents";
import { GameQueryRepository } from "../../repositories/GameQueryRepository";
import { useState } from "react";

export function useGameViewerPresenter() {
    const { busSubscriber } = useGameViewerContext();
    const [game, setGame] = useState<GameQueryRepository.Output | null>(null);

    useBusSubscriber({
        bus: busSubscriber,
        eventName: GameEvents.GAME_SELECTED,
        handler: setGame
    });

    return {
        game,
    };
}