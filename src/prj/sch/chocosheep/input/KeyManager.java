package prj.sch.chocosheep.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyManager implements KeyListener {
    private int keyCount = 256;
    private boolean[] keys;
    private boolean[] previousKeys;
    private boolean[] startKeys;

    public KeyManager() {
        init();
    }

    private void init() {
        keys = new boolean[keyCount];
        previousKeys = new boolean[keyCount];
        startKeys = new boolean[keyCount];
    }

    public void tick() {
        for (int i = 0; i < keyCount; i++) {
            startKeys[i] = !previousKeys[i] && keys[i];
        }

        previousKeys = keys.clone();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
}
