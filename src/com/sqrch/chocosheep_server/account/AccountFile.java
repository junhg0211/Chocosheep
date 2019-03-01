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
            try {
                save(file, password, friends, "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileAlreadyExistsException(path);
        }
    }

    private String id, password;
    private ArrayList<String> friends;
    private String name;

    private String path;
    private File file;

    AccountFile(String id, String password) throws IOException {
        this.id = id;
        this.password = password;

        init();
    }

    private void init() throws IOException {
        path = "./db/account/" + id + "." + EXTENSION;
        file = new File(path);

        if (file.exists()) {
            String[] data = load();
            if (!data[0].equals(password))
                throw new IOException();
        } else {
            throw new IOException();
        }
    }

    private void save() throws IOException {
        save(file, password, friends, name);
    }

    private static void save(File file, String password, ArrayList<String> friends, String name) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(password + "\n");
        fileWriter.write(String.join(" ", friends) + "\n");
        fileWriter.write(name + "\n");
        fileWriter.close();
    }

    private String[] load() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] result = new String[3];
        String line;
        for (int i = 0; (line = bufferedReader.readLine()) != null; i++) {
            result[i] = line;
        }
        password = result[0];
        friends = new ArrayList<>(Arrays.asList(result[1].split(" ")));
        name = result[2];
        return result;
    }
}
