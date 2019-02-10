package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.Card;
import prj.sch.chocosheep.rootobject.RootObject;
import prj.sch.chocosheep.rootobject.Tablecloth;

import java.awt.*;

public class Lobby extends State {
    private MouseManager mouseManager;
    private Display display;

    public Lobby(Root root, MouseManager mouseManager, Display display) {
        super(root);
        this.mouseManager = mouseManager;
        this.display = display;

        init();
    }

    private void init() {
        RootObject.add(new Tablecloth(display));
        RootObject.add(new Card(Card.Type.KAO, mouseManager, 100));
        RootObject.add(new Card(Card.Type.GARTAR, mouseManager, 150));
        RootObject.add(new Card(Card.Type.ROTTAR, mouseManager, 200));
        RootObject.add(new Card(Card.Type.ORGAN, mouseManager, 250));
        RootObject.add(new Card(Card.Type.SOYAR, mouseManager, 300));
        RootObject.add(new Card(Card.Type.BAAW, mouseManager, 350));
        RootObject.add(new Card(Card.Type.SORVOR, mouseManager, 400));
        RootObject.add(new Card(Card.Type.PHORE, mouseManager, 450));
        RootObject.add(new Card(Card.Type.BOVIE, mouseManager, 500));
        RootObject.add(new Card(Card.Type.BAINNE, mouseManager, 550));
        RootObject.add(new Card(Card.Type.BONAR, mouseManager, 600));
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Colors.BLACK);
        graphics2D.drawLine(0, 0, display.getWidth(), display.getHeight());
    }
}
