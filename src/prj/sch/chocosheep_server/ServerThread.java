package prj.sch.chocosheep_server;

import prj.sch.chocosheep_server.account.Account;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
            String message = scanner.nextLine();
            String[] messages = message.split(" ");

            if (messages.length < 1) break;

            if (messages[0].equalsIgnoreCase("PING")) {
                send("PONG");
            } else if (messages[0].equalsIgnoreCase("LGIN")) {
                if (messages.length == 1) {
                    if (account != null) {
                        send(account.getId());
                    } else {
                        send("ERRR 0");
                    }
                } else if (messages.length == 3) {
                    if (account == null) {
                        String id = messages[1];
                        String pw = messages[2];

                        try {
                            account = new Account(id, pw);
                        } catch (VerifyError e) {
                            send("ERRR 0");
                        }
                    } else {
                        send("ERRR 1");
                    }
                }
            }
        }

        disconnect();
    }

    private void send(String message) {
        printStream.println(message);
    }

    private void disconnect() {

    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
