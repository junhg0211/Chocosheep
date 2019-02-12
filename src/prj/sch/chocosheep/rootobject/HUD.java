package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.root.Root;

import java.awt.*;

public class HUD extends RootObject {
    private Root root;
    private TextFormat textFormat;

    private Text state, fps, cursor;

    public HUD(Root root, TextFormat textFormat) {
        this.root = root;
        this.textFormat = textFormat;

        init();
    }

    private void init() {
        state = new Text(0, (int) textFormat.getSize(), "", textFormat);
        fps = new Text(0, (int) (textFormat.getSize() * 2), "", textFormat);
        cursor = new Text(0, (int) (textFormat.getSize() * 3), "", textFormat);
    }

    @Override
    public void tick() {
        state.setText("s " + root.getState());
        fps.setText((int) root.getDisplay().getDisplayFps() + " fps");
        cursor.setText("c " + root.getMouseManager().getX() + " " + root.getMouseManager().getY());
    }

    @Override
    public void render(Graphics graphics) {
        state.render(graphics);
        fps.render(graphics);
        cursor.render(graphics);
    }
}
