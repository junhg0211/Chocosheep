package com.sqrch.chocosheep_server.account;

import java.io.IOException;
import java.util.ArrayList;

public class Account {
    public static Account newLogin(String id, String password) throws IOException {
        Account account = new Account(id);
        account.login(id, password);
        return account;
    }

    private String id, password;
    private ArrayList<String> friends;
    private AccountFile accountFile;

    private Account(String id) {
        this.id = id;

        init();
    }

    private void login(String id, String password) throws IOException {
        accountFile = AccountFile.login(id, password);
    }

    private void init() {
        friends = new ArrayList<>();
        accountFile = new AccountFile(id);
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public AccountFile getAccountFile() {
        return accountFile;
    }
}
