package prj.sch.chocosheep.state;

import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.RootObject;

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
}
