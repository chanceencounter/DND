package com.ericarao.dnd.model;

import org.immutables.value.Value;

import java.util.Set;

@DndStyle
@Value.Immutable
public interface ServerResponseIF {
    Set<Integer> getClientIds();
    NetworkPacket getResponse();
}
