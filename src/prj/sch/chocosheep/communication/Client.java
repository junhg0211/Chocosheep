package prj.sch.chocosheep.communication;

import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.AlertMessage;
import prj.sch.chocosheep.rootobject.RootObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Root root;
    private String host;
    private int port;

    private boolean connected;
    private PrintStream printStream;
    private Scanner scanner;
    private ClientThread clientThread;

    private String login;

    public Client(Root root, String host, int port) {
        this.root = root;
        this.host = host;
        this.port = port;

        init();
    }

    private void init() {
        login = "";
    }

    public void connect() throws IOException {
        Socket socket = new Socket(host, port);

        connected = true;

        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());

        clientThread = new ClientThread(root, this);

        start();
    }

    public void login(String id, String password) {
        clientThread.resetQueue();
        send("LGIN " + id + " " + password);
        String response = getQueue();
        if (response.equalsIgnoreCase("TMOT")) response = "LGIN TMOT";

        String[] responses = response.split(" ");

        if (responses[1].equalsIgnoreCase("PASS")) {
            RootObject.add(new AlertMessage("F8R3D9S T4DR8D!!", root.getDisplay()));
            login = id;
        } else {
            if (responses[1].equalsIgnoreCase("ERRR")) {
                switch (responses[2]) {
                    case "1":
                        RootObject.add(new AlertMessage("D6D9E9 EE8S3S Q9A9FQ4SG8R6 X3FF44TTT3QS9E6.", root.getDisplay()));
                        break;
                    case "2":
                        RootObject.add(new AlertMessage("D9A9 F8R3D9S E85D4 D9TTT3QS9E6.", root.getDisplay()));
                        break;
                }
                send("EXIT");
            } else if (responses[1].equalsIgnoreCase("TMOT")) {
                RootObject.add(new AlertMessage("T9R6S V8R86, E6T9 T9E8G63W2T41D88.", root.getDisplay()));
            }
            send("EXIT");
        }
    }

    public void logout() {
        clientThread.resetQueue();
        send("LGOT");

        login = "";
        RootObject.add(new AlertMessage("F8R3D6D2T E85D4TTT3QS9E6!", root.getDisplay()));
    }

    private String getQueue() {
        String response;
        long startTime = System.currentTimeMillis();
        do {
            response = clientThread.readQueue();
            if (System.currentTimeMillis() > startTime + 1000) return "TMOT";
        } while (response.equals(""));
        return response;
    }

    private void start() {
        clientThread.start();
    }

    private void send(String message) {
        System.out.println("SERVER <- " + message);
        printStream.println(message);
    }

    String recv() {
        String message = scanner.nextLine();
        System.out.println("SERVER -> " + message);
        return message;
    }

    boolean isConnected() {
        return connected;
    }

    public String getLogin() {
        return login;
    }
}
