package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.communication.Client;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Display;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ChattingOverlay extends RootObject {
    private static final int WIDTH = 500;

    private Display display;
    private Client client;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    private Rectangle background, onlinePlayerBackground;

    public ChattingOverlay(Display display, Client client, MouseManager mouseManager, KeyManager keyManager) {
        this.display = display;
        this.client = client;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        windowResize();
    }

    @Override
    public void tick() {
        if (mouseManager.getLeftEndClick()) {
            mouseManager.resetLeftEndClick();
            if ((mouseManager.getX() < background.getX() && WIDTH < mouseManager.getX())
                    || keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
                destroy();
            }
        }

        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            keyManager.resetStartKey(KeyEvent.VK_ESCAPE);
            destroy();
        }
    }

    @Override
    public void render(Graphics graphics) {
        background.render(graphics);
        onlinePlayerBackground.render(graphics);
    }

    @Override
    public void windowResize() {
        background = new Rectangle(display.getWidth() - WIDTH, 0, WIDTH, display.getHeight(),
                new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
        onlinePlayerBackground = new Rectangle(0, 0, WIDTH, display.getHeight(),
                new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
    }
}
