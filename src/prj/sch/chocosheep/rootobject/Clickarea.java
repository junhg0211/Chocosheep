package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.input.MouseManager;

public class Clickarea extends RootObject {
    private int x, y, w, h;
    private MouseManager mouseManager;

    public Clickarea(int x, int y, int w, int h, MouseManager mouseManager) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.mouseManager = mouseManager;
    }

    public boolean isClicked() {
        return mouseManager.getLeftEndClick()
                && x < mouseManager.getX() && mouseManager.getX() < x + w
                && y < mouseManager.getY() && mouseManager.getY() < y + h
                && x < mouseManager.getClickStartX() && mouseManager.getClickStartX() < x + w
                && y < mouseManager.getClickStartY() && mouseManager.getClickStartY() < y + h;
    }
}
