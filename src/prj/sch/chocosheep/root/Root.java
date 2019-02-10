package prj.sch.chocosheep.root;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.rootobject.HUD;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.state.Intro;
import prj.sch.chocosheep.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Root implements Runnable {
    private Display display;

    private State state;
    private HUD hud;

    private Thread thread;
    private boolean running;

    public Root() {
        init();
    }

    private void init() {
        display = new Display(1920, 1080, "Chocosheep", 60);

        state = new Intro(this, display);
        hud = new HUD(this, new TextFormat("./res/font/BMJUA_ttf.ttf", 12, Colors.GRAY));

        thread = new Thread(this);
        running = false;
    }

    private void tick() {
        state.tick();

        for (RootObject object : RootObject.objects) {
            object.tick();
        }
        RootObject.clearDeleteQueue();

        hud.tick();
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
        hud.render(graphics);

        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run() {
        double timePerLoop = 1000000000 / display.getFps();
        double delta = 0;

        long previousLoop, loop = System.nanoTime();

        while (running) {
            previousLoop = loop;
            loop = System.nanoTime();

            delta += (loop - previousLoop) / timePerLoop;
            if (delta >= 1) {
                delta--;

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

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
