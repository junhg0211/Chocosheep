package prj.sch.chocosheep.state;

import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.AlertMessage;
import prj.sch.chocosheep.rootobject.RootObject;

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
        if (root.getClient() == null) {
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
