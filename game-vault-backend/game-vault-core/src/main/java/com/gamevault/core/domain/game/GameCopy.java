package com.gamevault.core.domain.game;

public sealed interface GameCopy permits PhysicalGameCopy, DigitalGameCopy {

    GameFormat format();

}
