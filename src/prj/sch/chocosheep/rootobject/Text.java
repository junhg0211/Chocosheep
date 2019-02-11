package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.TextFormat;

import java.awt.*;

public class Text extends RootObject {
    private int x, y;
    private String text;
    private TextFormat textFormat;

    Text(int x, int y, String text, TextFormat textFormat) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textFormat = textFormat;
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(textFormat.getFont());
        graphics2D.setColor(textFormat.getColor());
        graphics2D.drawString(text, x, y);
    }

    int getWidth() {
        return textFormat.stringWidth(text);
    }

    int getHeight() {
        return textFormat.stringHeight(text);
    }

    Text addHeight() {
        y += getHeight();
        return this;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    void setText(String text) {
        this.text = text;
    }

    public TextFormat getTextFormat() {
        return textFormat;
    }
}
