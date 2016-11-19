package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractClientUpdate {
    public abstract int getNewHealth();
}
