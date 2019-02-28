package com.sqrch.chocosheep_server.account;

import java.io.IOException;
import java.util.ArrayList;

public class Account {
    private String id, password;
    private ArrayList<String> friends;
    private AccountFile accountFile;

    public Account(String id, String password) throws IOException {
        this.id = id;
        this.password = password;

        init();
    }

    private void init() throws IOException {
        friends = new ArrayList<>();
        accountFile = new AccountFile(id, password);
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }
}
