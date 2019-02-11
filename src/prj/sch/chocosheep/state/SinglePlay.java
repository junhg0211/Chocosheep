package prj.sch.chocosheep.state;

import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.SettingWindow;
import prj.sch.chocosheep.rootobject.Tablecloth;

import java.awt.*;

class SinglePlay extends State {
    private Display display;

    private Tablecloth tablecloth;

    private enum Situation {
        SETTING, PLAYING
    }
    private Situation situation;

    private SettingWindow settingWindow;

    SinglePlay(Root root, Display display) {
        super(root);
        this.display = display;

        init();
    }

    private void init() {
        tablecloth = new Tablecloth(display);

        situation = Situation.SETTING;

        settingWindow = new SettingWindow(display);
    }

    @Override
    public void tick() {
        if (situation == Situation.SETTING) {
            settingWindow.tick();
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
