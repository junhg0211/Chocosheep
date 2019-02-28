package com.sqrch.chocosheep_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Start {
    private boolean running = false;
    private static final int PORT = 31685;

    private ArrayList<ServerThread> serverThreads = new ArrayList<>();

    ServerThread getThreadById(String id) {
        for (ServerThread serverThread : serverThreads) {
            if (serverThread.getAccount().getId().equalsIgnoreCase(id)) {
                return serverThread;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        new Start().startServer();
    }

    private void startServer() throws IOException {
        if (running)
            return;
        running = true;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("로컬 서버가 포트 " + PORT + "에서 열렸습니다.");

        while (running) {
            Socket socket = serverSocket.accept();

            new ServerThread(socket, this).start();
        }
    }

    boolean isRunning() {
        return running;
    }

    ArrayList<ServerThread> getServerThreads() {
        return serverThreads;
    }
}
