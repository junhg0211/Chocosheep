package prj.sch.chocosheep_server.account;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccountFile {
    private final static String EXTENSION = "acc";

    public static void createNewFile(String id, String password) throws FileAlreadyExistsException {
        String path = "./db/account/" + id + "." + EXTENSION;
        File file = new File(path);

        if (!file.exists()) {
            try {
                save(file, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileAlreadyExistsException(path);
        }
    }

    private String id, password;

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

        Path pathFile = Paths.get(path);
        if (Files.exists(pathFile)) {
            load();
        } else {
            throw new IOException();
        }
    }

    private void save() throws IOException {
        save(file, password);
    }

    private static void save(File file, String password) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(password);
    }

    private void load() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            i++;
            if (i == 1) {
                password = line;
            }
        }
    }
}
