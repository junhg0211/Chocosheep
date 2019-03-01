package com.sqrch.chocosheep.communication;

import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.AlertMessage;
import com.sqrch.chocosheep.rootobject.RootObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private Root root;
    private String host;
    private int port;

    private boolean connected;
    private PrintStream printStream;
    private Scanner scanner;
    private ClientThread clientThread;

    private boolean login;
    private String lastMessage;
    private boolean lastMessageSend;

    public Client(Root root, String host, int port) {
        this.root = root;
        this.host = host;
        this.port = port;

        init();
    }

    private void init() {
        login = false;
        lastMessage = "";
    }

    public void connect() throws IOException {
        if (connected)
            return;
        Socket socket = new Socket(host, port);

        connected = true;

        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());

        clientThread = new ClientThread(root, this, root.getLanguageManager());

        start();
    }

    void disconnect() {
        if (login)
            logout();
        printStream = null;
        scanner = null;
        connected = false;
    }

    public void login(String id, String password) {
        send("LGIN " + id + " " + password);
    }

    public void logout() {
        send("LGOT");

        login = false;
        clientThread.resetLoginId();
        RootObject.add(new AlertMessage(root.getLanguageManager().getString("SuccessfulLogout"), root.getDisplay()));
    }

    public void register(String id, String password) {
        send("RGST " + id + " " + password);
    }

    private void start() {
        clientThread.start();
    }

    public String getId() {
        if (clientThread == null)
            return null;
        return clientThread.getLoginId();
    }

    public void refreshId() {
        send("LGIN");
    }

    void send(String message) {
        lastMessage = message;
        lastMessageSend = true;
        System.out.println("SERVER <- " + message);
        printStream.println(message);
    }

    String recv() {
        try {
            lastMessage = scanner.nextLine();
            lastMessageSend = false;
        } catch (NoSuchElementException e) {
            disconnect();
            return null;
        }
        System.out.println("SERVER -> " + lastMessage);
        return lastMessage;
    }

    boolean isConnected() {
        return connected;
    }

    public boolean isLogin() {
        return login;
    }

    void setLogin(boolean login) {
        this.login = login;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public boolean isLastMessageSend() {
        return lastMessageSend;
    }
}
