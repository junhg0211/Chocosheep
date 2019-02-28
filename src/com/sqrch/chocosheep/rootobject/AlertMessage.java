package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.functions.Positioning;
import com.sqrch.chocosheep.root.Display;

import java.awt.*;

public class AlertMessage extends RootObject {
    private static final TextFormat TEXT_FORMAT = new TextFormat(Const.FONT_PATH, 24, Color.WHITE);
    private static final int DURATION = 1000;

    private String message;
    private Display display;

    private int textX, textY;
    private long endTime;
    private int rectX, rectY, rectWidth, rectHeight;

    public AlertMessage(String message, Display display) {
        this.message = message;
        this.display = display;

        init();
    }

    private void init() {
        textX = Positioning.center(display.getWidth(), TEXT_FORMAT.stringWidth(message));
        textY = (int) (Positioning.center(display.getHeight(), TEXT_FORMAT.getSize()) + TEXT_FORMAT.getSize() - TEXT_FORMAT.getSize() * 0.2);
        endTime = System.currentTimeMillis() + DURATION;
        rectWidth = TEXT_FORMAT.stringWidth(message) + 20;
        rectHeight = TEXT_FORMAT.stringHeight(message) + 20;
        rectX = Positioning.center(display.getWidth(), rectWidth);
        rectY = Positioning.center(display.getHeight(), rectHeight);
    }

    @Override
    public void tick() {
        if (endTime < System.currentTimeMillis()) {
            destroy();
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics.setColor(Const.BLACK);
        graphics.fillRect(rectX, rectY, rectWidth, rectHeight);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(TEXT_FORMAT.getFont());
        graphics2D.setColor(TEXT_FORMAT.getColor());
        graphics2D.drawString(message, textX, textY);
    }
}
