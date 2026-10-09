package com.gamevault.core.domain.game;

import com.gamevault.core.domain.common.ItemCondition;

public record PhysicalGameCopy(
        ItemCondition condition,
        boolean hasBox,
        boolean hasManual
) implements GameCopy {

    @Override
    public GameFormat format() {
        return GameFormat.PHYSICAL;
    }
}
