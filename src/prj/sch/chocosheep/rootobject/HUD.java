package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.root.Root;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HUD extends RootObject {
    private Root root;
    private TextFormat textFormat;
    private KeyManager keyManager;

    private Text state, fps, cursor, objectCount, previewCard, connected, logged, lastMessage;

    private boolean show;

    public HUD(Root root, TextFormat textFormat, KeyManager keyManager) {
        this.root = root;
        this.textFormat = textFormat;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        state = new Text(0, (int) textFormat.getSize(), "", textFormat);
        fps = new Text(0, (int) (textFormat.getSize() * 2), "", textFormat);
        cursor = new Text(0, (int) (textFormat.getSize() * 3), "", textFormat);
        objectCount = new Text(0, (int) (textFormat.getSize() * 4), "", textFormat);
        previewCard = new Text(0, (int) (textFormat.getSize() * 5), "", textFormat);
        connected = new Text(0, (int) (textFormat.getSize() * 6), "", textFormat);
        logged = new Text(0, (int) (textFormat.getSize() * 7), "", textFormat);
        lastMessage = new Text(0, (int) (textFormat.getSize() * 8), "", textFormat);

        show = false;
    }

    @Override
    public void tick() {
        if (keyManager.getStartKeys()[KeyEvent.VK_F4]) {
            show = !show;
        }

        if (show) {
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
            logged.setText("l " + root.getClient().getLogin());
            lastMessage.setText("r " + root.getClient().isLastMessageSended() + " " + root.getClient().getLastMessage());
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (show) {
            state.render(graphics);
            fps.render(graphics);
            cursor.render(graphics);
            objectCount.render(graphics);
            previewCard.render(graphics);
            connected.render(graphics);
            logged.render(graphics);
            lastMessage.render(graphics);
        }
    }
}
