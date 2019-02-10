package prj.sch.chocosheep.root;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.state.Intro;
import prj.sch.chocosheep.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Root implements Runnable {
    private Display display;

    private State state;

    private Thread thread;
    private boolean running;

    public Root() {
        init();
    }

    private void init() {
        display = new Display(1920, 1080, "Chocosheep", 60);

        state = new Intro(display);

        thread = new Thread(this);
        running = false;
    }

    private void tick() {
        state.tick();
        for (RootObject object : RootObject.objects) {
            object.tick();
        }
        RootObject.clearDeleteQueue();
    }

    private void render() {
        BufferStrategy bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Colors.BACKGROUND);
        graphics.fillRect(0, 0, display.getWidth(), display.getHeight());

        state.render(graphics);
        for (RootObject object : RootObject.objects) {
            object.render(graphics);
        }

        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run() {
        while (running) {
            double a = System.currentTimeMillis();
            if (System.currentTimeMillis() - a > 0.2) {
                tick();
                render();
            }
        }

        stop();
    }

    public void start() {
        if (running)
            return;
        running = true;
        thread.start();
    }

    private void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
