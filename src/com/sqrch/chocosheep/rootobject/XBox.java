package com.sqrch.chocosheep.rootobject;

import java.awt.*;

public class XBox extends RootObject {
    private int x, y, width, height;

    XBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 127));
        graphics.drawRect(x, y, width, height);
        graphics.drawLine(x, y, x + width, y + height);
        graphics.drawLine(x, y + height, x + width, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    void setHeight(int height) {
        this.height = height;
    }
}
