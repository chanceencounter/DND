package com.ericarao.dnd.utils;

import com.ericarao.dnd.model.NetworkPacket;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedWriter;
import java.io.IOException;

public class NetworkUtils {
    public static void write(BufferedWriter out, NetworkPacket networkPacket) {
        try {
            out.write(marshallPacket(networkPacket));
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String marshallPacket(NetworkPacket networkPacket) {
        try {
            return JsonUtils.MAPPER.writeValueAsString(networkPacket);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
