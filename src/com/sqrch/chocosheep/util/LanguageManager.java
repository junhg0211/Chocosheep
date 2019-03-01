package com.sqrch.chocosheep.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class LanguageManager {
    public enum Language {
        KOREAN
    }

    private Language language;

    private File file;
    private HashMap<String, String> map;

    public LanguageManager(Language language) {
        this.language = language;

        init();
    }

    private void init() {
        if (language == Language.KOREAN) {
            file = new File("./res/language/korean.txt");
        }

        map = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split("=");
                map.put(lines[0], lines[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        if (map.containsKey(key))
            return map.get(key);

        throw new NullPointerException();
    }
}
