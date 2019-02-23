package prj.sch.chocosheep.communication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;

    private Socket socket;
    private boolean connected;
    private PrintStream printStream;
    private Scanner scanner;
    private ClientThread clientThread;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        init();
    }

    private void init() throws IOException {
        socket = new Socket(host, port);

        connected = true;

        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());

        clientThread = new ClientThread(this);
    }

    public void start() {
        clientThread.start();
    }

    public void send(String message) {
        printStream.println(message);
    }

    public String recv() {
        return scanner.nextLine();
    }

    public boolean isConnected() {
        return connected;
    }
}
