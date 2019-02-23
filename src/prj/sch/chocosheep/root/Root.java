package prj.sch.chocosheep.root;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.communication.Client;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.rootobject.Card;
import prj.sch.chocosheep.rootobject.HUD;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.state.Lobby;
import prj.sch.chocosheep.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Root implements Runnable {
    private MouseManager mouseManager;
    private KeyManager keyManager;
    private Display display;
    private State state;
    private HUD hud;
    private Client client;

    private Thread thread;
    private boolean running;

    public Root() {
        init();
    }

    private void init() {
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
        display = new Display(1920, 1080, "Chocosheep", 60, mouseManager, keyManager, this);

        state = new Lobby(this, mouseManager, keyManager, display);
        hud = new HUD(this, new TextFormat(Const.FONT_PATH, 12, Const.BLACK));

        thread = new Thread(this);
        running = false;

        client = new Client(this, Const.SERVER_HOST, Const.SERVER_PORT);
    }

    void windowResize() {
        for (RootObject object : RootObject.objects) {
            object.windowResize();
        }
        try {
            state.windowResize();
        } catch (NullPointerException ignored) {}
    }

    private void tick() {
        Card.previousPreviewing = Card.previewing;

        mouseManager.tick();
        keyManager.tick();

        state.tick();

        RootObject.sumAddQueue();
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

        graphics.setColor(Const.WHITE);
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
        long timePerLoop = (long) (1000 / display.getFps());
        long previousLoop, loop = System.currentTimeMillis();
        double delta = 0;

        long previousFrame, frame = System.currentTimeMillis();

        while (running) {
            previousLoop = loop;
            loop = System.currentTimeMillis();
            delta += (double) (loop - previousLoop) / timePerLoop;
            try {
                Thread.sleep(timePerLoop / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    public Display getDisplay() {
        return display;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
