package prj.sch.chocosheep_server.account;

import java.io.*;

class AccountFile {
    private String id;
    private String pw;

    private File file;

    AccountFile(String id) {
        this.id = id;

        init();
    }

    private void init() {
        file = new File("./db/accounts/" + id + ".txt");

        boolean fileCreated;
        try {
            fileCreated = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (fileCreated) {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void save() throws IOException {
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(id);
    }

    private void load() throws IOException {
        FileReader fileReader = new FileReader(file);

        StringBuilder stringBuilder = new StringBuilder();
        int i;
        while ((i = fileReader.read()) != -1) {
            stringBuilder.append(i);
        }
        String data = stringBuilder.toString();

        String[] splitData = data.split("\n");

        id = splitData[0];
    }
}
