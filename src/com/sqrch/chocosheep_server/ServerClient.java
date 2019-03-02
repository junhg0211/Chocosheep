package com.sqrch.chocosheep_server;

import com.sqrch.chocosheep_server.account.Account;
import com.sqrch.chocosheep_server.account.AccountFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

class ServerClient extends Thread {
    private Socket socket;
    private Start start;

    private Scanner scanner;
    private PrintStream printStream;
    private Account account;

    ServerClient(Socket socket, Start start) throws IOException {
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
                        send("LGIN " + account.getAccountFile().getId());
                    } else {
                        send("LGIN ERRR 0");
                    }
                } else if (messages.length == 3) {
                    if (account == null) {
                        String id = messages[1];
                        String password = messages[2];

                        try {
                            account = Account.newLogin(id, password);
                            send("LGIN PASS 0");
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
                        AccountFile.createNewFile(id, password, new ArrayList<>());
                        send("RGST PASS 0");
                    } catch (FileAlreadyExistsException e) {
                        send("RGST ERRR 0");
                    }
                }
            } else if (messages[0].equalsIgnoreCase("LGOT")) {
                if (account != null) {
                    account = null;
                    send("LGOT PASS 0");
                } else {
                    send("LGOT ERRR 0");
                }
            } else if (messages[0].equalsIgnoreCase("CHAT")) {
                if (account != null) {
                    ServerClient serverClient = start.getThreadById(messages[1]);
                    if (serverClient != null) {
                        serverClient.send("CHAT CHAT " +
                                String.join(" ", Arrays.copyOfRange(messages, 2, messages.length)));
                        send("CHAT SUCC 0");
                    } else {
                        send("CHAT ERRR 1");
                    }
                } else {
                    send("CHAT ERRR 0");
                }
            } else if (messages[0].equalsIgnoreCase("FRND")) {
//                ServerClient serverClient = start.getThreadById(messages[1]); // TODO NOTIFICATION THAT YOU ARE ADDED TO SOMEONE'S FRIEND LIST!
                if (account != null) {
                    if (messages[2].equalsIgnoreCase("TRUE")) {
                        if (!account.getFriends().contains(messages[1])) {
                            account.getFriends().add(messages[1]);
                            send("FRND SUCC 0");
                        } else {
                            send("FRND ERRR 1");
                        }
                    } else if (messages[1].equalsIgnoreCase("FLSE")) {
                        if (account.getFriends().contains(messages[1])) {
                            account.getFriends().remove(messages[1]);
                            send("FRND SUCC 1");
                        } else {
                            send("FRND ERRR 2");
                        }
                    }
                } else {
                    send("FRND ERRR 0");
                }
            } else if (messages[0].equalsIgnoreCase("EXIT")) {
                send("EXIT");
                break;
            } else if (messages[0].equalsIgnoreCase("NAME")) {
                if (messages.length == 1) {
                    if (account != null) {
                        String name = account.getAccountFile().getName();
                        if (!name.equals("")) {
                            send("NAME " + account.getAccountFile().getName());
                        } else {
                            send("NAME ERRR 1");
                        }
                    } else {
                        send("NAME ERRR 0");
                    }
                } else {
                    if (messages[1].equalsIgnoreCase("REST")) {
                        if (account != null) {
                            account.getAccountFile().resetName();
                            send("NAME SUCC 0");
                        } else {
                            send("NAME ERRR 2");
                        }
                    } else {
                        if (account != null) {
                            account.getAccountFile().setName(String.join(" ", Arrays.copyOfRange(messages, 1, messages.length)));
                            send("NAME SUCC 1");
                        } else {
                            send("NAME ERRR 3");
                        }
                    }
                }
            } else if (messages[0].equalsIgnoreCase("GUSR")) {
                ArrayList<String> names = new ArrayList<>();
                for (ServerClient serverClient : start.getServerClients()) {
                    try {
                        String id = serverClient.getAccount().getAccountFile().getId();
                        if (id.toLowerCase().contains(messages[1].toLowerCase())) {
                            names.add(id);
                        }
                    } catch (NullPointerException ignored) {}
                }
                if (!names.isEmpty()) {
                    send("GUSR " + String.join(" ", names));
                } else {
                    send("GUSR ERRR 0");
                }
            } else if (messages[0].equalsIgnoreCase("GNME")) {
                AccountFile accountFile = new AccountFile(messages[1]);
                if (accountFile.getName() != null) {
                    if (!accountFile.getName().equals("")) {
                        send("GNME " + messages[1] + " " + accountFile.getName());
                    } else {
                        send("GNME ERRR 1");
                    }
                } else {
                    send("GNME ERRR 0");
                }
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
        start.getServerClients().add(this);
        System.out.println(socket.getInetAddress() + "이(가) 접속했습니다.");
    }

    private void disconnected() {
        start.getServerClients().remove(this);
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
