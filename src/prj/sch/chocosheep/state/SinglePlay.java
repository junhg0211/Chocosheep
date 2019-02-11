package prj.sch.chocosheep.state;

import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.SettingWindow;
import prj.sch.chocosheep.rootobject.Tablecloth;

import java.awt.*;
import java.awt.event.KeyEvent;

class SinglePlay extends State {
    private Root root;
    private Display display;
    private KeyManager keyManager;

    private Tablecloth tablecloth;

    private enum Situation {
        SETTING, PLAYING
    }
    private Situation situation;

    private SettingWindow settingWindow;

    private int rounds;
    private boolean sortedOrder;

    SinglePlay(Root root, Display display, KeyManager keyManager) {
        this.root = root;
        this.display = display;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        tablecloth = new Tablecloth(display);

        situation = Situation.SETTING;

        settingWindow = new SettingWindow(display, keyManager);
    }

    @Override
    public void tick() {
        if (situation == Situation.SETTING) {
            settingWindow.tick();

            if (keyManager.getStartKeys()[KeyEvent.VK_ENTER]) {
                rounds = settingWindow.getRounds();
                sortedOrder = settingWindow.isSortedOrder();
                situation = Situation.PLAYING;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        tablecloth.render(graphics);

        if (situation == Situation.SETTING) {
            settingWindow.render(graphics);
        }
    }
}
