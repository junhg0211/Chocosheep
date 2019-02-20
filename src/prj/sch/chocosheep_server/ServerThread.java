package prj.sch.chocosheep_server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class ServerThread extends Thread {
    private Socket socket;
    private Start start;

    private Scanner scanner;
    private PrintStream printStream;

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
            String message = scanner.nextLine();
            String[] messages = message.split(" ");

            if (messages.length < 1) break;
        }

        disconnect();
    }

    private void disconnect() {

    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
