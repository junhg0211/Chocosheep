package prj.sch.chocosheep.communication;

class ClientThread extends Thread {
    private Client client;

    ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        String message;
        String[] messages;
        while (client.isConnected()) {
            message = client.recv();
//            messages = message.split(" ");

            System.out.println(message);
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
