package prj.sch.chocosheep.rootobject;

import java.awt.*;
import java.util.ArrayList;

public class RootObject {
    public static ArrayList<RootObject> objects = new ArrayList<>();
    private static ArrayList<RootObject> destroyQueue = new ArrayList<>();

    public static void add(RootObject object) {
        objects.add(object);
    }

    public static void clearDeleteQueue() {
        for (RootObject object : destroyQueue) {
            objects.remove(object);
        }
    }

    public void tick() {}

    public void render(Graphics graphics) {}

    public void destroy() {
        destroyQueue.add(this);
    }

    public void windowResize() {}
}
