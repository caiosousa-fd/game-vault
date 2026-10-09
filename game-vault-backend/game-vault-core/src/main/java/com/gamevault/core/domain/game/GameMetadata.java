package com.gamevault.core.domain.game;

import com.gamevault.core.domain.common.ItemRegion;
import lombok.Builder;
import lombok.NonNull;

import java.net.URI;

@Builder(toBuilder = true)
public record GameMetadata(
        @NonNull ItemRegion region,
        String edition,
        URI coverUrl,
        String notes
) {
    public GameMetadata {
        if (edition != null && edition.isBlank()) {
            throw new IllegalArgumentException(
                    "Edition must be null or contain text"
            );
        }
    }
}
