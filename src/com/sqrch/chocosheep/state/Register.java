package com.sqrch.chocosheep.state;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.communication.Client;
import com.sqrch.chocosheep.functions.Positioning;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Display;
import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.*;
import com.sqrch.chocosheep.rootobject.TextField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

class Register extends State {
    private Root root;
    private Display display;
    private Client client;
    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Text register;
    private TextField id, password;
    private Clickarea apply;

    Register(Root root, Display display, Client client, KeyManager keyManager, MouseManager mouseManager) {
        this.root = root;
        this.display = display;
        this.client = client;
        this.keyManager = keyManager;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        TextFormat textFormat = new TextFormat(Const.FONT_PATH, 64, Const.WHITE);
        TextFormat registerTextFormat = new TextFormat(Const.FONT_PATH, 64, Const.BLACK);

        register = new Text(Positioning.center(display.getWidth(), display.getWidth() / 2), (int) (Positioning.center(display.getHeight(), textFormat.getSize() * 2 + 20) - 20), "G85D21SR6D9Q", registerTextFormat);
        id = new TextField(register.getX(), (int) Positioning.center(display.getHeight(), textFormat.getSize() * 2 + 20), display.getWidth() / 2, textFormat, Const.BLACK, mouseManager, keyManager);
        password = new TextField(id.getX(), (int) (id.getY() + textFormat.getSize() + 20), id.getWidth(), textFormat, Const.BLACK, mouseManager, keyManager);
        password.setType(TextField.Type.PASSWORD);

        apply = new Clickarea(id.getX(), (int) (password.getY() + textFormat.getSize() + 20), id.getWidth(), (int) textFormat.getSize(), mouseManager);
    }

    @Override
    public void tick() {
        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            root.setState(new Setting(root, mouseManager, keyManager));
        }

        id.tick();
        password.tick();
        apply.tick();

        if (apply.isClicked()) {
            System.out.println("Hasd");
            String id = this.id.getText(), password = this.password.getText();
            if (id.equals("") || password.equals("")) {
                RootObject.add(new AlertMessage("S63D88DD3F D9QF44RG63W2T41D88.", display));
            } else {
                try {
                    client.connect();
                    client.register(id, password);
                } catch (IOException e) {
                    RootObject.add(new AlertMessage("T4Q4D41 W4QT8RG6F T2 D4QTT3QS9E6.", display));
                }
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        register.render(graphics);
        id.render(graphics);
        password.render(graphics);
        apply.render(graphics);
    }

    @Override
    public void windowResize() {
        id.setX(Positioning.center(display.getWidth(), display.getWidth() / 2));
        id.setY((int) Positioning.center(display.getHeight(), id.getHeight() * 2 + 20));
        id.setWidth(display.getWidth() / 2);
        password.setX(id.getX());
        password.setY((int) (id.getY() + id.getHeight() + 20));
        password.setWidth(display.getWidth() / 2);
        apply.setX(id.getX());
        apply.setY((int) (password.getY() + password.getHeight() + 20));
        apply.setWidth(id.getWidth());
        apply.windowResize();
    }
}
