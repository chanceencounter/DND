package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractRegisterClient {
    public abstract String getPlayerName();
    public abstract String getPlayerClass();
    public abstract int getPlayerLevel();
    public abstract int getPlayerHP();
    public abstract int getPlayerStr();
    public abstract int getPlayerDex();
    public abstract int getPlayerCon();
    public abstract int getPlayerInt();
    public abstract int getPlayerWis();
    public abstract int getPlayerCha();
    public abstract int getPlayerInitiative();
}
