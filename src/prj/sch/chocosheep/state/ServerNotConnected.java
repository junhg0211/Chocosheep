package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.rootobject.Text;

import java.awt.*;

public class ServerNotConnected extends State {
    private Display display;

    private Text text;

    public ServerNotConnected(Display display) {
        this.display = display;

        init();
    }

    private void init() {
        text = new Text(0, 0, "T4Q4D41 D44SR44FG6F T2 D4QTT3QS9E6.",
                new TextFormat(Const.FONT_PATH, 72, Const.BLACK));
        text.setX(Positioning.center(display.getWidth(), text.getWidth()));
        text.setY(Positioning.center(display.getHeight(), text.getHeight()) + text.getHeight());
    }

    @Override
    public void render(Graphics graphics) {
        text.render(graphics);
    }
}
