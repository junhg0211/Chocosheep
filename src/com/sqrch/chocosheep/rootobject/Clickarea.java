package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.input.MouseManager;

import java.awt.*;

public class Clickarea extends RootObject {
    private int x, y, width, height;
    private MouseManager mouseManager;

    private XBox xBox;

    public Clickarea(int x, int y, int width, int height, MouseManager mouseManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        xBox = new XBox(x, y, width, height);
    }

    public boolean isClicked() {
        int mouseX = mouseManager.getX(), mouseY = mouseManager.getY();
        int startX = mouseManager.getClickStartX(), startY = mouseManager.getClickStartY();
        return mouseManager.getLeftEndClick()
                && x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height
                && x < startX && startX < x + width && y < startY && startY < y + height;
    }

    @Override
    public void render(Graphics graphics) {
        xBox.render(graphics);
    }

    @Override
    public void windowResize() {
        xBox.setX(x);
        xBox.setY(y);
        xBox.setWidth(width);
        xBox.setHeight(height);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
