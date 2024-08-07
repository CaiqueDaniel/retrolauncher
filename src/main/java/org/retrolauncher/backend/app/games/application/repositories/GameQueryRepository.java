package org.retrolauncher.backend.app.games.application.repositories;

import org.retrolauncher.backend.app.games.application.dtos.*;

import java.util.List;

public interface GameQueryRepository {
    List<GameSearchResult> search(GameSearchParams params);
}
