package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractUpdatePlayerStats implements NetworkPacket {
    public abstract int getPlayerLevel();
    public abstract int getPlayerHP();
    public abstract int getPlayerStr();
    public abstract int getPlayerDex();
    public abstract int getPlayerCon();
    public abstract int getPlayerInt();
    public abstract int getPlayerWis();
    public abstract int getPlayerCha();
    public abstract int getPlayerInitiative();
    public abstract int getDamageHP();
    public abstract String getStatusEffect();
    public abstract int getSavingThrow();


    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.UpdatePlayerStats;
    }
}
