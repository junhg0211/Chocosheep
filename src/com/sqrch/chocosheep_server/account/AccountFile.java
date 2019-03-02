package com.sqrch.chocosheep_server.account;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountFile {
    private final static String EXTENSION = "acc";

    public static void createNewFile(String id, String password, ArrayList<String> friends)
            throws FileAlreadyExistsException {
        String path = "./db/account/" + id + "." + EXTENSION;
        File file = new File(path);

        if (!file.exists()) {
            save(file, password, friends, "");
        } else {
            throw new FileAlreadyExistsException(path);
        }
    }

    static AccountFile login(String id, String password) throws IOException {
        AccountFile accountFile = new AccountFile(id);
        accountFile.login(password);
        return accountFile;
    }

    private String id, password;
    private ArrayList<String> friends;
    private String name;

    private File file;

    public AccountFile(String id) {
        this.id = id;

        init();
    }

    private void init() {
        file = new File("./db/account/" + id + "." + EXTENSION);

        load();
    }

    private void login(String password) throws IOException {
        this.password = password;

        @SuppressWarnings("TooBroadScope")
        String insertedPassword = password;

        if (file.exists()) {
            String[] data = load();
            if (!data[0].equals(insertedPassword))
                throw new IOException();
        } else {
            throw new IOException();
        }
    }

    private void save() {
        save(file, password, friends, name);
    }

    private static void save(File file, String password, ArrayList<String> friends, String name) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(password + "\n");
            fileWriter.write(String.join(" ", friends) + "\n");
            fileWriter.write(name + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] load() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }
        String[] result = new String[3];
        String line;
        try {
            for (int i = 0; (line = bufferedReader.readLine()) != null; i++) {
                result[i] = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        password = result[0];
        friends = new ArrayList<>(Arrays.asList(result[1].split(" ")));
        name = result[2];
        return result;
    }

    public void resetName() {
        setName("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        save();
    }

    public String getId() {
        return id;
    }
}
