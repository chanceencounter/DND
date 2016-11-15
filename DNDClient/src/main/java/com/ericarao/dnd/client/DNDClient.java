package com.ericarao.dnd.client;

import com.ericarao.dnd.core.NetworkClient;

public class DNDClient {

    public static void main(String args[]) {

        System.out.println("You are currently running the \"Client\"");

        new NetworkClient("127.0.0.1", 8000).run();
    }

}