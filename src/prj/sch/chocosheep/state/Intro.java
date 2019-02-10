package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.TextTimeSize;

import java.awt.*;

public class Intro extends State {
    private Display display;

    private TextTimeSize chocosheep;

    public Intro(Root root, Display display) {
        super(root);

        this.display = display;

        init();
    }

    private void init() {
        TextFormat textFormat = new TextFormat("./res/font/BMJUA_ttf.ttf", 72, Colors.GRAY);
        chocosheep = new TextTimeSize(0, 0, "Chocosheep", textFormat, 1000, 0);
    }

    @Override
    public void tick() {
        int[] chocosheepBounds = chocosheep.getBounds();
        chocosheep.setX(Positioning.center(display.getWidth(), chocosheepBounds[0]));
        chocosheep.setY(Positioning.center(display.getHeight(), chocosheepBounds[1]) + chocosheepBounds[1] / 2);
        chocosheep.tick();

        if (chocosheep.isEnd()) {
            super.root.setState(new Lobby(super.root));
        }
    }

    @Override
    public void render(Graphics graphics) {
        chocosheep.render(graphics);
    }
}
