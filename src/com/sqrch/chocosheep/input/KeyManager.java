package com.sqrch.chocosheep.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private int keyCount = 256;
    private boolean[] keys;
    private boolean[] previousKeys;
    private boolean[] startKeys;
    private boolean startKey;

    private String contents;

    public KeyManager() {
        init();
    }

    private void init() {
        keys = new boolean[keyCount];
        previousKeys = new boolean[keyCount];
        startKeys = new boolean[keyCount];
        startKey = false;
        contents = "";
    }

    public void tick() {
        startKey = false;
        for (int i = 0; i < keyCount; i++) {
            startKeys[i] = !previousKeys[i] && keys[i];
            startKey = true;
        }

        previousKeys = keys.clone();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        contents += e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            keys[e.getKeyCode()] = true;
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            keys[e.getKeyCode()] = false;
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    public boolean[] getStartKeys() {
        return startKeys;
    }

    public void resetStartKey(int index) {
        startKeys[index] = false;
    }

    public boolean isStartKey() {
        return startKey;
    }

    public String getContents() {
        return contents;
    }

    public void resetContents() {
        this.contents = "";
    }
}

