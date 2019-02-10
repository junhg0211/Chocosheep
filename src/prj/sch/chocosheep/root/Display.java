package prj.sch.chocosheep.root;

import prj.sch.chocosheep.rootobject.RootObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Display {
    private int width, height;
    private String title;
    private double fps;

    private Canvas canvas;

    Display(int width, int height, String title, double fps) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.fps = fps;

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
                width = component.getWidth();
                height = component.getHeight();
                for (RootObject object : RootObject.objects) {
                    object.windowResize();
                }
            }
        });

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getFps() {
        return fps;
    }

    Canvas getCanvas() {
        return canvas;
    }
}
