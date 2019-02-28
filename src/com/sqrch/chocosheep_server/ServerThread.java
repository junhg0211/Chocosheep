package com.sqrch.chocosheep_server;

import com.sqrch.chocosheep_server.account.Account;
import com.sqrch.chocosheep_server.account.AccountFile;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

class ServerThread extends Thread {
    private Socket socket;
    private Start start;

    private Scanner scanner;
    private PrintStream printStream;
    private Account account;

    ServerThread(Socket socket, Start start) throws IOException {
        this.socket = socket;
        this.start = start;

        init();
    }

    private void init() throws IOException {
        scanner = new Scanner(socket.getInputStream());
        printStream = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (start.isRunning()) {
            String message;
            try {
                message = recv();
            } catch (NoSuchElementException e) {
                break;
            }
            String[] messages = message.split(" ");

            if (messages.length < 1) break;

            if (messages[0].equalsIgnoreCase("PING")) {
                send("PING PONG");
            } else if (messages[0].equalsIgnoreCase("LGIN")) {
                if (messages.length == 1) {
                    if (account != null) {
                        send("LGIN " + account.getId());
                    } else {
                        send("LGIN ERRR 0");
                    }
                } else if (messages.length == 3) {
                    if (account == null) {
                        String id = messages[1];
                        String password = messages[2];

                        try {
                            account = new Account(id, password);
                            send("LGIN PASS");
                        } catch (IOException e) {
                            send("LGIN ERRR 1");
                        }
                    } else {
                        send("LGIN ERRR 2");
                    }
                }
            } else if (messages[0].equalsIgnoreCase("RGST")) {
                if (messages.length == 3) {
                    String id = messages[1];
                    String password = messages[2];
                    try {
                        AccountFile.createNewFile(id, password);
                        send("RGST PASS");
                    } catch (FileAlreadyExistsException e) {
                        send("RGST ERRR 0");
                    }
                }
            } else if (messages[0].equalsIgnoreCase("LGOT")) {
                if (account != null) {
                    account = null;
                    send("LGOT PASS");
                } else {
                    send("LGOT ERRR 0");
                }
                send("EXIT");
                break;
            } else if (messages[0].equalsIgnoreCase("CHAT")) {
                if (account != null) {
                    ServerThread serverThread = start.getThreadById(messages[1]);
                    if (serverThread != null) {
                        serverThread.send("CHAT CHAT " + String.join(" ", Arrays.copyOfRange(messages, 2, messages.length)));
                        send("CHAT SUCC");
                    } else {
                        send("CHAT ERRR 1");
                    }
                } else {
                    send("CHAT ERRR 0");
                }
            } else if (messages[0].equalsIgnoreCase("EXIT")) {
                send("EXIT");
                break;
            }
        }

        disconnected();
    }

    private String recv() {
        String message = scanner.nextLine();
        if (!message.substring(0, 4).equalsIgnoreCase("PING"))
            System.out.println(socket.getInetAddress() + " -> " + message);
        return message;
    }

    private void send(String message) {
        if (!message.substring(0, 4).equalsIgnoreCase("PING"))
            System.out.println(socket.getInetAddress() + " <- " + message);
        printStream.println(message);
    }

    private void connected() {
        start.getServerThreads().add(this);
        System.out.println(socket.getInetAddress() + "이(가) 접속했습니다.");
    }

    private void disconnected() {
        start.getServerThreads().remove(this);
        System.out.println(socket.getInetAddress() + "이(가) 퇴장했습니다.");
    }

    @Override
    public synchronized void start() {
        connected();
        super.start();
    }

    Account getAccount() {
        return account;
    }
}