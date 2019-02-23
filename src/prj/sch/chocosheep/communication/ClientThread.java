package prj.sch.chocosheep.communication;

import prj.sch.chocosheep.root.Root;

class ClientThread extends Thread {
    private Root root;
    private Client client;

    private String queue;

    ClientThread(Root root, Client client) {
        this.root = root;
        this.client = client;

        init();
    }

    private void init() {
        queue = "";
    }


    void resetQueue() {
        queue = "";
    }

    String readQueue() {
        String tmp = queue;
        queue = "";
        return tmp;
    }

    @Override
    public void run() {
        String message;
        String[] messages;
        while (client.isConnected()) {
            message = client.recv();
            messages = message.split(" ");

            if (messages[0].equals("EXIT")) {
                break;
            } else {
                queue = message;
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
