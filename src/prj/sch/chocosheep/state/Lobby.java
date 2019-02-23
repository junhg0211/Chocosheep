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

    private Clickarea toSinglePlay, toMultiPlay, toSetting;

    public Lobby(Root root, MouseManager mouseManager, KeyManager keyManager, Display display) {
        this.root = root;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;
        this.display = display;

        init();
    }

    private void init() {
        toSinglePlay = new Clickarea(0, 0, display.getWidth() / 2, display.getHeight() / 2, mouseManager);
        toMultiPlay = new Clickarea(display.getWidth() / 2, 0, display.getWidth() / 2, display.getHeight() / 2, mouseManager);
        toSetting = new Clickarea(0, display.getHeight() / 2, display.getWidth() / 2, display.getHeight() / 2, mouseManager);
    }

    @Override
    public void tick() {
        if (toSinglePlay.isClicked()) {
            root.setState(new SinglePlay(root, display, keyManager, mouseManager));
        } else if (toMultiPlay.isClicked()) {
            try {
                root.setState(new MultiPlay(root, keyManager, root.getDisplay()));
            } catch (Exception ignored) {}
        } else if (toSetting.isClicked()) {
            root.setState(new Setting(root, keyManager));
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        toSinglePlay.render(graphics);
        toMultiPlay.render(graphics);
        toSetting.render(graphics);

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Const.BLACK);
    }

    @Override
    public void windowResize() {
        toSinglePlay.setWidth(display.getWidth() / 2);
        toSinglePlay.setHeight(display.getHeight());
    }
}
