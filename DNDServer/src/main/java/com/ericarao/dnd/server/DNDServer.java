package com.ericarao.dnd.server;

import com.ericarao.dnd.core.NetworkServer;

import java.util.Scanner;


/**
 * Created by Doraemon on 10/26/16.
 */
public class DNDServer {

    public static void main(String args[]) {

        int port;
        int playerNum;

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

}
