package com.sqrch.chocosheep.state;

import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.root.Display;
import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.AlertMessage;
import com.sqrch.chocosheep.rootobject.RootObject;

import java.awt.event.KeyEvent;

class MultiPlay extends State {
    private Root root;
    private KeyManager keyManager;
    private Display display;

    MultiPlay(Root root, KeyManager keyManager, Display display) throws Exception {
        this.root = root;
        this.keyManager = keyManager;
        this.display = display;

        init();
    }

    private void init() throws Exception {
        if (!root.getClient().getLogin()) {
            RootObject.add(new AlertMessage("A4FX9V3FF41D9F3F D25G63T4S3S F8R3D9S G63 W2T41D88!", display));
            throw new Exception();
        }
    }

    @Override
    public void tick() {
        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            root.setState(new Lobby(root, root.getMouseManager(), keyManager, display));
        }
    }
}
