package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.communication.Client;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class ChattingOverlay extends RootObject {
    private static final int WIDTH = 500;

    private static String[] users;

    private Display display;
    private Client client;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    private Rectangle background, onlinePlayerBackground;
    private TextField userSearch;
    private String[] onlineUser;

    public ChattingOverlay(Display display, Client client, MouseManager mouseManager, KeyManager keyManager) {
        this.display = display;
        this.client = client;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        windowResize();

        userSearch = new TextField(50, 50, WIDTH - 100, new TextFormat(Const.FONT_PATH, 20, Const.WHITE),
                Const.BLACK, mouseManager, keyManager) {
            @Override
            public void withInserting() {
                if (!this.getText().equals("")) {
                    client.send("GUSR " + this.getText());
                }
            }
        };
    }

    @Override
    public void tick() {
        userSearch.tick();

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
        onlinePlayerBackground.render(graphics);
        userSearch.render(graphics);
        TextFormat textFormat = new TextFormat(Const.FONT_PATH, 16, Const.WHITE);
        try {
            for (int i = 0; i < users.length; i++) {
                new Text(50, (int) (100 + i * textFormat.getSize()), users[i], textFormat).render(graphics);
            }
        } catch (NullPointerException ignored) {}

        background.render(graphics);
    }

    @Override
    public void windowResize() {
        background = new Rectangle(display.getWidth() - WIDTH, 0, WIDTH, display.getHeight(),
                new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
        onlinePlayerBackground = new Rectangle(0, 0, WIDTH, display.getHeight(),
                new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
    }

    public static void setUsers(String[] users) {
        ChattingOverlay.users = users;
    }
}
