package prj.sch.chocosheep_server;

import prj.sch.chocosheep_server.account.Account;
import prj.sch.chocosheep_server.account.AccountFile;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
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

        connected();
    }

    @Override
    public void run() {
        while (start.isRunning()) {
            String message;
            try {
                message = scanner.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
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
                        String password = messages[2];

                        try {
                            account = new Account(id, password);
                            send("PASS");
                        } catch (IOException e) {
                            send("ERRR 0");
                        }
                    } else {
                        send("ERRR 1");
                    }
                }
            } else if (messages[0].equalsIgnoreCase("RGST")) {
                if (messages.length == 3) {
                    String id = messages[1];
                    String password = messages[2];
                    try {
                        AccountFile.createNewFile(id, password);
                        send("PASS");
                    } catch (FileAlreadyExistsException e) {
                        send("ERRR 0");
                    }
                }
            } else if (messages[0].equalsIgnoreCase("LGOT")) {
                if (account != null) {
                    account = null;
                    send("PASS");
                } else {
                    send("ERRR 0");
                }
            }
        }

        disconnected();
    }

    private void send(String message) {
        printStream.println(message);
    }

    private void connected() {
        System.out.println(socket.getInetAddress() + "이(가) 접속했습니다.");
    }

    private void disconnected() {
        System.out.println(socket.getInetAddress() + "이(가) 퇴장했습니다.");
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
