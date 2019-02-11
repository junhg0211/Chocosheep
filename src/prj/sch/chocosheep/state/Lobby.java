package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.Clickarea;

import java.awt.*;

public class Lobby extends State {
    private MouseManager mouseManager;
    private Display display;

    private Clickarea toGame;

    public Lobby(Root root, MouseManager mouseManager, Display display) {
        super(root);
        this.mouseManager = mouseManager;
        this.display = display;

        init();
    }

    private void init() {
        toGame = new Clickarea(0, 0, display.getWidth() / 2, display.getHeight(), mouseManager);
    }

    @Override
    public void tick() {
        if (toGame.isClicked()) {
            super.root.setState(new SinglePlay(super.root, display));
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Colors.BLACK);
    }
}
