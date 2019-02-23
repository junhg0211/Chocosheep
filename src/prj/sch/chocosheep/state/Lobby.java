package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.Clickarea;

import java.awt.*;

public class Lobby extends State {
    private Root root;
    private MouseManager mouseManager;
    private KeyManager keyManager;
    private Display display;

    private Clickarea toSinglePlay, toMultiPlay;

    public Lobby(Root root, MouseManager mouseManager, KeyManager keyManager, Display display) {
        this.root = root;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;
        this.display = display;

        init();
    }

    private void init() {
        toSinglePlay = new Clickarea(0, 100, display.getWidth() / 2, display.getHeight(), mouseManager);
        toMultiPlay = new Clickarea(display.getWidth() / 2, 100, display.getWidth() / 2, display.getHeight(), mouseManager);
    }

    @Override
    public void tick() {
        if (toSinglePlay.isClicked()) {
            root.setState(new SinglePlay(root, display, keyManager, mouseManager));
        } else if (toMultiPlay.isClicked()) {
            root.setState(new MultiPlay());
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Const.BLACK);
    }

    @Override
    public void windowResize() {
        toSinglePlay.setWidth(display.getWidth() / 2);
        toSinglePlay.setHeight(display.getHeight());
    }
}
