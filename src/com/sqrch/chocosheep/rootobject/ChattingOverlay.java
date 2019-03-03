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
    private static final int USERS_HEIGHT = 100;

    private static String[] users = new String[0];
    private static boolean usersChanged = false;

    private Display display;
    private Client client;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    private Rectangle background, onlinePlayerBackground;
    private TextField userSearch;
    private String[] onlineUser;
    private Clickarea[] usersClickarea;

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

        usersClickarea = new Clickarea[0];
    }

    @Override
    public void tick() {
        userSearch.tick();

        if (mouseManager.getLeftEndClick()) {
            if ((mouseManager.getX() < background.getX() && WIDTH < mouseManager.getX())
                    || keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
                mouseManager.resetLeftEndClick();
                destroy();
            }
        }

        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            keyManager.resetStartKey(KeyEvent.VK_ESCAPE);
            destroy();
        }

        if (usersChanged) {
            usersClickarea = new Clickarea[(int) Math.ceil((double) (display.getHeight() - 150) / USERS_HEIGHT)];

            if (users != null) {
                for (int i = 0; i < usersClickarea.length && i < users.length; i++) {
                    usersClickarea[i] = new Clickarea(50, 150 + i * USERS_HEIGHT, WIDTH - 100, USERS_HEIGHT, mouseManager) {
                        @Override
                        public boolean isClicked() {
                            return super.isClicked();
                        }
                    };
                }
            } else {
                usersClickarea = null;
            }

            usersChanged = false;
        }

        try {
            //noinspection ConstantConditions
            for (int i = 0; i < usersClickarea.length; i++) {
                if (usersClickarea[i] == null) continue;

                if (usersClickarea[i].isClicked()) {
                    System.out.println(users[i]);
                }
            }
        } catch (NullPointerException ignored) {}
    }

    @Override
    public void render(Graphics graphics) {
        onlinePlayerBackground.render(graphics);
        userSearch.render(graphics);
        try {
            for (Clickarea clickarea : usersClickarea) {
                clickarea.render(graphics);
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
        ChattingOverlay.usersChanged = true;
    }
}
