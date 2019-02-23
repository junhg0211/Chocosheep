package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.root.Root;

import java.awt.*;

public class HUD extends RootObject {
    private Root root;
    private TextFormat textFormat;

    private Text state;
    private Text fps;
    private Text cursor;
    private Text objectCount;
    private Text previewCard;
    private Text connected;

    public HUD(Root root, TextFormat textFormat) {
        this.root = root;
        this.textFormat = textFormat;

        init();
    }

    private void init() {
        state = new Text(0, (int) textFormat.getSize(), "", textFormat);
        fps = new Text(0, (int) (textFormat.getSize() * 2), "", textFormat);
        cursor = new Text(0, (int) (textFormat.getSize() * 3), "", textFormat);
        objectCount = new Text(0, (int) (textFormat.getSize() * 4), "", textFormat);
        previewCard = new Text(0, (int) (textFormat.getSize() * 5), "", textFormat);
        connected = new Text(0, (int) (textFormat.getSize() * 6), "", textFormat);
    }

    @Override
    public void tick() {
        state.setText("s " + root.getState());
        fps.setText((int) root.getDisplay().getDisplayFps() + " fps");
        cursor.setText("c " + root.getMouseManager().getX() + " " + root.getMouseManager().getY());
        objectCount.setText("o " + RootObject.objects.size());
        if (Card.previewing != null) {
            previewCard.setText("p " + Card.previewing + " " + Card.previewing.getType() + " " + Card.previewing.isPreviewing());
        } else {
            previewCard.setText("p null");
        }
        if (root.getClient() != null) {
            connected.setText("s " + root.getClient());
        } else {
            connected.setText("s null");
        }
    }

    @Override
    public void render(Graphics graphics) {
        state.render(graphics);
        fps.render(graphics);
        cursor.render(graphics);
        objectCount.render(graphics);
        previewCard.render(graphics);
        connected.render(graphics);
    }
}
