package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.root.Root;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HUD extends RootObject {
    private Root root;
    private TextFormat textFormat;
    private KeyManager keyManager;

    private Text state, fps, cursor, objectCount, previewCard, focusedTextField, connected, logged, lastMessage;

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
        focusedTextField = new Text(0, (int) (textFormat.getSize() * 6), "", textFormat);
        connected = new Text(0, (int) (textFormat.getSize() * 7), "", textFormat);
        logged = new Text(0, (int) (textFormat.getSize() * 8), "", textFormat);
        lastMessage = new Text(0, (int) (textFormat.getSize() * 9), "", textFormat);

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
            focusedTextField.setText("t " + TextField.getFocused());
            if (root.getClient().isConnected()) {
                connected.setText("s " + root.getClient());
            } else {
                connected.setText("s null");
            }
            logged.setText("l " + root.getClient().isLogin());
            lastMessage.setText("r " + root.getClient().isLastMessageSend() + " " + root.getClient().getLastMessage());
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
            focusedTextField.render(graphics);
            connected.render(graphics);
            logged.render(graphics);
            lastMessage.render(graphics);
        }
    }
}
