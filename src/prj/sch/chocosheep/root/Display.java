package prj.sch.chocosheep.root;

import prj.sch.chocosheep.input.KeyboardManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.rootobject.RootObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ConcurrentModificationException;

public class Display {
    private int width, height;
    private String title;
    private double fps, displayFps;
    private MouseManager mouseManager;
    private KeyboardManager keyboardManager;

    private Canvas canvas;

    Display(int width, int height, String title, double fps, MouseManager mouseManager, KeyboardManager keyboardManager) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.fps = fps;
        this.mouseManager = mouseManager;
        this.keyboardManager = keyboardManager;

        init();
    }

    private void init() {
        JFrame frame = new JFrame(title);
        frame.setSize(width + 16, height + 39);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component component = e.getComponent();
                width = component.getWidth() - 16;
                height = component.getHeight() - 39;
                try {
                    for (RootObject object : RootObject.objects) {
                        object.windowResize();
                    }
                } catch (ConcurrentModificationException ignored) {}
            }
        });
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        frame.addKeyListener(keyboardManager);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.addMouseListener(mouseManager);
        canvas.addMouseMotionListener(mouseManager);

        frame.add(canvas);
        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    double getFps() {
        return fps;
    }

    Canvas getCanvas() {
        return canvas;
    }

    public double getDisplayFps() {
        return displayFps;
    }

    public void setDisplayFps(double displayFps) {
        this.displayFps = displayFps;
    }
}
