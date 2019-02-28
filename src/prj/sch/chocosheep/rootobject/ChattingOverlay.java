package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ChattingOverlay extends RootObject {
    private static final int WIDTH = 500;

    private Display display;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    private Rectangle background;

    public ChattingOverlay(Display display, MouseManager mouseManager, KeyManager keyManager) {
        this.display = display;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        background = new Rectangle(display.getWidth() - WIDTH, 0, WIDTH, display.getHeight(),
                new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
    }

    @Override
    public void tick() {
        if (mouseManager.getLeftEndClick()) {
            mouseManager.resetLeftEndClick();
            if (mouseManager.getX() < background.getX() || keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
                destroy();
            }
        }

        if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
            keyManager.resetStartKey(KeyEvent.VK_ESCAPE);
            destroy();
        }
    }

    @Override
    public void render(Graphics graphics) {
        background.render(graphics);
    }
}
