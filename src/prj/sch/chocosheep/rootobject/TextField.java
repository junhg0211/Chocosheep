package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.input.MouseManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextField extends RootObject {
    private int x, y, width;
    private TextFormat textFormat;
    private Color backgroundColor;
    private MouseManager mouseManager;
    private KeyManager keyManager;

    public enum Type {
        NORMAL, PASSWORD;
    }

    private boolean inserting;
    private String text;
    private String clippedText;
    private Type type;

    public TextField(int x, int y, int width, TextFormat textFormat, Color backgroundColor,
                     MouseManager mouseManager, KeyManager keyManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.textFormat = textFormat;
        this.backgroundColor = backgroundColor;
        this.mouseManager = mouseManager;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        inserting = false;
        text = "";
        clippedText = "";
        type = Type.NORMAL;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void resetText() {
        text = "";
    }

    @Override
    public void tick() {
        if (mouseManager.getStartClick()[0]) {
            int mouseX = mouseManager.getX(), mouseY = mouseManager.getY();
            inserting = x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + textFormat.getSize();
            if (inserting) {
                keyManager.resetContents();
            }
        }

        if (inserting) {
            if (keyManager.isStartKey()) {
                if (keyManager.getStartKeys()[KeyEvent.VK_ENTER]) {
                    inserting = false;
                } else {
                    text += keyManager.getContents();
                    keyManager.resetContents();

                    if (text.length() >= 1) {
                        if (text.charAt(text.length() - 1) == '\b') {
                            if (text.length() >= 2) {
                                text = text.substring(0, text.length() - 2);
                            } else {
                                text = "";
                            }
                        }
                    }
                }
            }
        }

        clippedText = (type == Type.NORMAL ? text : "*".repeat(text.length())) + (inserting ? "_" : "");
        int i = 0;
        while (textFormat.stringWidth(clippedText) >= width) {
            i++;
            clippedText = text.substring(i);
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(x, y, width, (int) textFormat.getSize());
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(textFormat.getColor());
        graphics2D.setFont(textFormat.getFont());
        graphics2D.drawString(clippedText, x, (float) (y + textFormat.getSize() - textFormat.getSize() * 0.2));
    }

    public String getText() {
        return text;
    }
}
