package com.sqrch.chocosheep.state;

import com.sqrch.chocosheep.rootobject.RootObject;

import java.awt.*;
import java.util.ArrayList;

public class State {
    private ArrayList<RootObject> objects = new ArrayList<>();

    public void tick() {
        for (RootObject object : objects) {
            object.tick();
        }
    }

    public void render(Graphics graphics) {
        for (RootObject object : objects) {
            object.render(graphics);
        }
    }

    public void windowResize() {}
}
