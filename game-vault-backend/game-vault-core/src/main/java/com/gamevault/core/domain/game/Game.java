package com.gamevault.core.domain.game;

import com.gamevault.core.domain.common.AcquisitionDetails;
import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder(toBuilder = true)
public record Game(
        @NonNull UUID id,
        @NonNull String title,
        @NonNull UUID platformId,
        @NonNull GameMetadata metadata,
        @NonNull GameCopy copy,
        @NonNull GameStatus status,
        @NonNull AcquisitionDetails acquisitionDetails
) {
    public Game {
        if (title.isBlank()) {
            throw new IllegalArgumentException(
                    "Title must not be blank"
            );
        }
    }
}
