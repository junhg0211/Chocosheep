package prj.sch.chocosheep_server.account;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

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

        if (file.exists()) {
            String[] data = load();
            if (!data[0].equals(password))
                throw new IOException();
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

    private String[] load() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] result = new String[1];
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (i == 0) {
                result[i] = line;
            }
            i++;
        }
        return result;
    }
}
