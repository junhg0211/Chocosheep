package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.input.MouseManager;

public class Clickarea extends RootObject {
    private int x, y, width, height;
    private MouseManager mouseManager;

    public Clickarea(int x, int y, int width, int height, MouseManager mouseManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouseManager = mouseManager;
    }

    public boolean isClicked() {
        return mouseManager.getLeftEndClick()
                && x < mouseManager.getX() && mouseManager.getX() < x + width
                && y < mouseManager.getY() && mouseManager.getY() < y + height
                && x < mouseManager.getClickStartX() && mouseManager.getClickStartX() < x + width
                && y < mouseManager.getClickStartY() && mouseManager.getClickStartY() < y + height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
