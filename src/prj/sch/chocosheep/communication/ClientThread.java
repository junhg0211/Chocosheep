package prj.sch.chocosheep.communication;

import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.AlertMessage;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.state.Setting;

class ClientThread extends Thread {
    private Root root;
    private Client client;

    private String loginId;

    ClientThread(Root root, Client client) {
        this.root = root;
        this.client = client;
    }

    @Override
    public void run() {
        String message;
        String[] messages;
        while (client.isConnected()) {
            message = client.recv();
            messages = message.split(" ");

            if (messages[0].equalsIgnoreCase("LGIN")) {
                if (messages[1].equalsIgnoreCase("PASS")) {
                    RootObject.add(new AlertMessage("F8R3D9S T4DR8D!!", root.getDisplay()));
                    client.setLogin(true);
                } else if (messages[1].equalsIgnoreCase("ERRR")) {
                        switch (messages[2]) {
                            case "1":
                                RootObject.add(new AlertMessage("D6D9E9 EE8S3S Q9A9FQ4SG8R6 X3FF44TTT3QS9E6.",
                                        root.getDisplay()));
                                break;
                            case "2":
                                RootObject.add(new AlertMessage("D9A9 F8R3D9S E85D4 D9TTT3QS9E6.",
                                        root.getDisplay()));
                                break;
                        }
                        client.send("EXIT");
                    client.send("EXIT");
                } else if (messages[1].equalsIgnoreCase("TMOT")) {
                    RootObject.add(new AlertMessage("T9R6S V8R86, E6T9 T9E8G63W2T41D88.",
                            root.getDisplay()));
                    client.send("EXIT");
                } else {
                    loginId = messages[1];
                }
            }
            else if (messages[0].equalsIgnoreCase("RGST")) {
                if (messages[1].equalsIgnoreCase("PASS")) {
                    RootObject.add(new AlertMessage("R441W4D T63DT4D T4DR8D!!", root.getDisplay()));
                    root.setState(new Setting(root, root.getMouseManager(), root.getKeyManager()));
                } else if (messages[1].equalsIgnoreCase("ERRR")) {
                    if (messages[2].equalsIgnoreCase("0")) {
                        RootObject.add(new AlertMessage("D6D9E9R6 D9A9 W8SW63G6QS9E6.", root.getDisplay()));
                    }
                } else if (messages[1].equalsIgnoreCase("TMOT")) {
                    RootObject.add(new AlertMessage("T9R6S V8R86, E6T9 T9E8G63W2T41D88.", root.getDisplay()));
                }
            }
            else if (messages[0].equals("EXIT")) {
                break;
            }
        }

        client.disconnect();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    String getLoginId() {
        return loginId;
    }

    void resetLoginId() {
        this.loginId = null;
    }
}
