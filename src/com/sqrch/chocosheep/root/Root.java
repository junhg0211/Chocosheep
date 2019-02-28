package com.sqrch.chocosheep.root;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.communication.Client;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.rootobject.Card;
import com.sqrch.chocosheep.rootobject.ChattingOverlay;
import com.sqrch.chocosheep.rootobject.HUD;
import com.sqrch.chocosheep.rootobject.RootObject;
import com.sqrch.chocosheep.rootobject.TextField;
import com.sqrch.chocosheep.state.Lobby;
import com.sqrch.chocosheep.state.State;

import java.awt.*;
import java.awt.event.KeyEvent;
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
        hud = new HUD(this, new TextFormat(Const.FONT_PATH, 12, Const.BLACK), keyManager);

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

        try {
            TextField.refreshFocused();
        } catch (NullPointerException ignored) {}

        mouseManager.tick();
        keyManager.tick();

        if (keyManager.getStartKeys()[KeyEvent.VK_M]) {
            if (RootObject.getObjectByClassType(ChattingOverlay.class) == null) {
                if (TextField.getFocused() == null)
                    RootObject.add(new ChattingOverlay(display, mouseManager, keyManager));
            }
        }

        RootObject.sumAddQueue();
        for (RootObject object : RootObject.objects) {
            object.tick();
        }
        RootObject.clearDeleteQueue();

        state.tick();

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

    public KeyManager getKeyManager() {
        return keyManager;
    }
}
