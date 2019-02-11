package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.root.Display;

import java.awt.*;

public class SettingWindow extends RootObject {
    private Display display;

    public SettingWindow(Display display) {
        this.display = display;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 127));
    }
}
