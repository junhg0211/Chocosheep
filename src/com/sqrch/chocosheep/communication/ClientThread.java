package com.sqrch.chocosheep.communication;

import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.AlertMessage;
import com.sqrch.chocosheep.rootobject.RootObject;
import com.sqrch.chocosheep.state.Setting;
import com.sqrch.chocosheep.util.LanguageManager;

import java.util.Arrays;

public class ClientThread extends Thread {
    private Root root;
    private Client client;
    private LanguageManager languageManager;

    private String loginId;
    private String nickname;

    ClientThread(Root root, Client client, LanguageManager languageManager) {
        this.root = root;
        this.client = client;
        this.languageManager = languageManager;
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
                    RootObject.add(new AlertMessage(languageManager.getString("SuccessfulLogin"),
                            root.getDisplay()));
                    client.setLogin(true);
                } else if (messages[1].equalsIgnoreCase("ERRR")) {
                        switch (messages[2]) {
                            case "1":
                                RootObject.add(new AlertMessage(languageManager.getString("IdOrPasswordInvalid"),
                                        root.getDisplay()));
                                break;
                            case "2":
                                RootObject.add(new AlertMessage(languageManager.getString("AlreadyLogin"),
                                        root.getDisplay()));
                                break;
                        }
                        client.send("EXIT");
                    client.send("EXIT");
                } else if (messages[1].equalsIgnoreCase("TMOT")) {
                    RootObject.add(new AlertMessage(languageManager.getString("TimeOut"), root.getDisplay()));
                    client.send("EXIT");
                } else {
                    loginId = messages[1];
                }
            } else if (messages[0].equalsIgnoreCase("RGST")) {
                if (messages[1].equalsIgnoreCase("PASS")) {
                    RootObject.add(new AlertMessage(languageManager.getString("SuccessfulRegistration"),
                            root.getDisplay()));
                    root.setState(new Setting(root, root.getMouseManager(), root.getKeyManager(),
                            root.getLanguageManager()));
                } else if (messages[1].equalsIgnoreCase("ERRR")) {
                    if (messages[2].equalsIgnoreCase("0")) {
                        RootObject.add(new AlertMessage(languageManager.getString("IdAlreadyExists"),
                                root.getDisplay()));
                    }
                } else if (messages[1].equalsIgnoreCase("TMOT")) {
                    RootObject.add(new AlertMessage(languageManager.getString("TimeOut"), root.getDisplay()));
                }
            } else if (messages[0].equalsIgnoreCase("LGOT")) {
                break;
            } else if (messages[0].equalsIgnoreCase("EXIT")) {
                break;
            } else if (messages[0].equalsIgnoreCase("NAME")) {
                if (!messages[1].equalsIgnoreCase("ERRR")) {
                    nickname = String.join(" ", Arrays.copyOfRange(messages, 1, messages.length));
                }
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

    public String getNickname() {
        return nickname;
    }

    void resetNickname() {
        nickname = null;
    }
}
