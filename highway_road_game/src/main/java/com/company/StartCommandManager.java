package com.company;

import java.util.Scanner;

public class StartCommandManager {
    boolean isServer = false;
    boolean online = false;
    boolean isDriver = false;
    Scanner scanner = new Scanner(System.in);

    public String takeCommands() {
        String name = "";
        String command = scanner.nextLine();
        boolean goNext = false;
        while(!goNext) {
            if (command.equals("online")) {
                boolean goNext2 = false;
                online = true;
                while (!goNext2) {
                    command = scanner.nextLine();
                    if (command.equals("drive")) {
                        isDriver = true;
                        System.out.println("Please, enter your player name");
                        command = scanner.nextLine();
                        name = command;
                        goNext2 = true;
                    } else if (command.equals("spam")) {
                        isDriver = false;
                        goNext2 = true;
                    } else if (command.equals("server")) {
                        isServer = true;
                        goNext2 = true;
                    } else {
                        System.out.println("unavailable command, you can choose 3 modes 'drive'," +
                                " 'spam' and 'server'");
                    }
                }
                goNext = true;
            } else if (command.equals("desktop")) {
                online = false;
                System.out.println("Please, enter your player name");
                command = scanner.nextLine();
                name = command;
                goNext = true;
            } else {
                System.out.println("unavailable command, please use commands 'online' or 'desktop'");
                command = scanner.nextLine();
            }
        }
        return name;
    }

    public void start(){
        String name = takeCommands();
        String command;
        if (online) {
            if (isDriver) {
                Multiplayer MP = new Multiplayer();
                MP.connectDriver("localhost", 8083, name);
            } else if (isServer) {
                Multiplayer MP = new Multiplayer();
                MP.StartServer("localhost", 8083);
                command = scanner.nextLine();
                if (command.equals("shut down")) {
                    MP.StopServer();
                }
            } else {
                Multiplayer MP = new Multiplayer();
                MP.connectSpamer("localhost", 8083, name);
            }
        } else {
            RoadLogic roadLogic = new RoadLogic(false);
            RaceLayout RL = new RaceLayout();
            RL.StartLayout(1, roadLogic, name);
        }
    }

}

