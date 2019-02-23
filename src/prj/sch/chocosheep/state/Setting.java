package prj.sch.chocosheep.state;

import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.root.Root;

import java.awt.event.KeyEvent;

class Setting extends State {
    private Root root;
    private KeyManager keyManager;

    Setting(Root root, KeyManager keyManager) {
        this.root = root;
        this.keyManager = keyManager;
    }

    @Override
    public void tick() {
        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            root.setState(new Lobby(root, root.getMouseManager(), keyManager, root.getDisplay()));
        }
    }
}
