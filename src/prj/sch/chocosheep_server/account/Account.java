package prj.sch.chocosheep_server.account;

import java.io.IOException;

public class Account {
    private String id, password;
    private AccountFile accountFile;

    public Account(String id, String password) throws IOException {
        this.id = id;
        this.password = password;

        init();
    }

    private void init() throws IOException {
        accountFile = new AccountFile(id, password);
    }

    public String getId() {
        return id;
    }
}
