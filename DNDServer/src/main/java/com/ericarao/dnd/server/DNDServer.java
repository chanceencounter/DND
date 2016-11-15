package com.ericarao.dnd.server;

import com.ericarao.dnd.core.NetworkServer;
import com.ericarao.dnd.core.model.RegisterPlayer;
import com.ericarao.dnd.core.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class DNDServer {

    public static void main(String args[]) {

        int port;
        int playerNum;

        test();
        Scanner input = new Scanner(System.in);
        System.out.println("You are currently running the \"Server\"\n" +
                "What port are you using (default if not specified)?");

        if (!input.hasNextInt()) {
            port = 8000;
            System.out.println("Defaulting to port 8000.");
            input.next();
        } else {
            port = input.nextInt();
            System.out.println(port + " input for port number.");
        }

        System.out.println("How many players are playing?");
        if (!input.hasNextInt()) {
            playerNum = 4;
            System.out.println("Defaulting to 4 players.");
        } else {
            playerNum = input.nextInt();
            System.out.println(playerNum + " input for number of players.");
        }


        new NetworkServer(port, playerNum).run();

    }

    public static void test() {
        RegisterPlayer createPlayer = RegisterPlayer.builder()
                .setPlayerName("")
                .setPlayerClass("")
                .setPlayerHP(10)
                .setPlayerLevel(1)
                .setPlayerStr(10)
                .setPlayerDex(10)
                .setPlayerCon(10)
                .setPlayerInt(10)
                .setPlayerWis(10)
                .setPlayerCha(10)
                .setPlayerInitiative(8)
                .build();

        try {
            System.out.println(JsonUtils.MAPPER.writeValueAsString(createPlayer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
