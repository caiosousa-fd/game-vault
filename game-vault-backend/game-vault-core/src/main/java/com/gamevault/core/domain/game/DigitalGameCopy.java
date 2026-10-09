package com.gamevault.core.domain.game;

public final class DigitalGameCopy implements GameCopy {

    @Override
    public GameFormat format() {
        return GameFormat.DIGITAL;
    }
}
