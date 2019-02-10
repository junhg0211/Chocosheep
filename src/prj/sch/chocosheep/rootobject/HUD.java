package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.root.Root;

import java.awt.*;

public class HUD extends RootObject {
    private Root root;
    private TextFormat textFormat;

    private Text state;

    public HUD(Root root, TextFormat textFormat) {
        this.root = root;
        this.textFormat = textFormat;

        init();
    }

    private void init() {
        state = new Text(0, (int) textFormat.getSize(), "", textFormat);
    }

    @Override
    public void tick() {
        state.setText("s: " + root.getState());
    }

    @Override
    public void render(Graphics graphics) {
        state.render(graphics);
    }
}
