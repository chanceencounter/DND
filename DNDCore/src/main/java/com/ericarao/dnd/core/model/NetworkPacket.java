package com.ericarao.dnd.core.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * JsonTypeInfo tells Jackson where to look when it needs to determine
 * which specific implementation of NetworkPacket it is deserializing.
 *
 * JsonSubTypes provide a mapping between specific values of the property
 * indicated by JsonTypeInfo to specific implementations of NetworkPacket.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientUpdate.class, name = "ClientUpdate"),
        @JsonSubTypes.Type(value = RegisterPlayer.class, name = "RegisterPlayer"),
        @JsonSubTypes.Type(value = PlayerLogin.class, name = "PlayerLogin"),
        @JsonSubTypes.Type(value = PlayerLoginResponse.class, name = "PlayerLoginResponse"),
        @JsonSubTypes.Type(value = PlayerUpdateStatsDM.class, name = "PlayerUpdateStatsDM")
})
public interface NetworkPacket {
    PacketType getType();
}
