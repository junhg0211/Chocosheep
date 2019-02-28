package com.sqrch.chocosheep.state;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.TextField;
import com.sqrch.chocosheep.rootobject.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Setting extends State {
    private Root root;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    private Text login;
    private TextField id, password;
    private Clickarea registerButton, logInOutButton;

    private Text nowLogin;

    public Setting(Root root, MouseManager mouseManager, KeyManager keyManager) {
        this.root = root;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        login = new Text(92, 146, "F8R3D9S", new TextFormat(Const.FONT_PATH, 50, Const.BLACK));
        TextFormat loginTextFieldTextFormat = new TextFormat(Const.FONT_PATH, 24, Const.WHITE);
        id = new TextField(92, 166, 278, loginTextFieldTextFormat, Const.BLACK, mouseManager, keyManager);
        password = new TextField(92, 206, 278, loginTextFieldTextFormat, Const.BLACK, mouseManager, keyManager);
        password.setType(TextField.Type.PASSWORD);
        registerButton = new Clickarea(92, 246, 129, (int) loginTextFieldTextFormat.getSize(), mouseManager);
        logInOutButton = new Clickarea(241, 246, 129, (int) loginTextFieldTextFormat.getSize(), mouseManager);

        nowLogin = new Text(92, 206, "F8R3D9S E85A", new TextFormat(Const.FONT_PATH, 24, Const.BLACK));
    }

    @Override
    public void tick() {
        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            root.setState(new Lobby(root, root.getMouseManager(), keyManager, root.getDisplay()));
        }

        logInOutButton.tick();
        if (!root.getClient().getLogin()) {
            id.tick();
            password.tick();
            registerButton.tick();

            if (logInOutButton.isClicked()) {
                try {
                    root.getClient().connect();
                    root.getClient().login(id.getText(), password.getText());
                } catch (IOException e) {
                    RootObject.add(new AlertMessage("T4Q4D41 W4QT8RG6F T2 D4QTT3QS9E6.", root.getDisplay()));
                }
            } else if (registerButton.isClicked()) {
                root.setState(new Register(root, root.getDisplay(), root.getClient(), keyManager, mouseManager));
            }
        } else {
            if (root.getClient().getId() == null) {
                root.getClient().refreshId();
            }
            nowLogin.setText(root.getClient().getId() + " W4QT8R W2D");
            if (logInOutButton.isClicked()) {
                id.resetText();
                password.resetText();
                root.getClient().logout();
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        login.render(graphics);
        logInOutButton.render(graphics);
        if (!root.getClient().getLogin()) {
            id.render(graphics);
            password.render(graphics);
            registerButton.render(graphics);
        } else {
            nowLogin.render(graphics);
        }
    }
}