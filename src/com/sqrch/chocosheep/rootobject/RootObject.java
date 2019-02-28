package com.sqrch.chocosheep.rootobject;

import java.awt.*;
import java.util.ArrayList;

public class RootObject {
    public static ArrayList<RootObject> objects = new ArrayList<>();
    private static ArrayList<RootObject> destroyQueue = new ArrayList<>();
    private static ArrayList<RootObject> addQueue = new ArrayList<>();

    public static RootObject getObjectByClassType(Class<ChattingOverlay> rootObjectClass) {
        for (RootObject object : objects) {
            if (object.getClass() == rootObjectClass) {
                return object;
            }
        }
        return null;
    }

    public static void add(RootObject object) {
        addQueue.add(object);
    }

    public static void sumAddQueue() {
        objects.addAll(addQueue);
        addQueue.clear();
    }

    public static void clearDeleteQueue() {
        for (RootObject object : destroyQueue) {
            objects.remove(object);
        }
        destroyQueue.clear();
    }

    public void tick() {}

    public void render(Graphics graphics) {}

    void destroy() {
        destroyQueue.add(this);
    }

    public void windowResize() {}
}
