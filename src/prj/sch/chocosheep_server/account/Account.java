package prj.sch.chocosheep_server.account;

public class Account {
    private String id, password;
    private AccountFile accountFile;

    public Account(String id, String password) {
        this.id = id;
        this.password = password;

        init();
    }

    private void init() {
        accountFile = new AccountFile(id);
    }

    public String getId() {
        return id;
    }
}
