package prj.sch.chocosheep.root;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.input.KeyboardManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.rootobject.Card;
import prj.sch.chocosheep.rootobject.HUD;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.rootobject.Tablecloth;
import prj.sch.chocosheep.state.Lobby;
import prj.sch.chocosheep.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Root implements Runnable {
    private MouseManager mouseManager;
    private KeyboardManager keyboardManager;
    private Display display;
    private State state;
    private HUD hud;

    private Thread thread;
    private boolean running;

    public Root() {
        init();
    }

    private void init() {
        mouseManager = new MouseManager();
        keyboardManager = new KeyboardManager();
        display = new Display(1920, 1080, "Chocosheep", 60, mouseManager, keyboardManager);

        state = new Lobby(this, mouseManager, display);
        hud = new HUD(this, new TextFormat("./res/font/BMJUA_ttf.ttf", 12, Colors.BLACK));

        thread = new Thread(this);
        running = false;
    }

    private void tick() {
        state.tick();

        Card.previousPreviewing = Card.previewing;
        mouseManager.tick();
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

        graphics.setColor(Colors.WHITE);
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
        long previousFrame, frame = System.currentTimeMillis();

        while (running) {
            previousLoop = loop;
            loop = System.nanoTime();

            delta += (loop - previousLoop) / timePerLoop;
            if (delta >= 1) {
                delta--;

                previousFrame = frame;
                frame = System.currentTimeMillis();
                display.setDisplayFps(1000.0 / (frame - previousFrame));

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
