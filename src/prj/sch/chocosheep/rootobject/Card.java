package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.input.MouseManager;

import java.awt.*;

public class Card extends RootObject {
    public static Card previewing, previousPreviewing;

    public enum Type { KAO, GARTAR, ROTTAR, ORGAN, SOYAR, BAAW, SORVOR, PHORE, BOVIE, BAINNE, BONAR }

    private Type type;
    private MouseManager mouseManager;

    private int x, y, w, h, shadowDepth, roundness;
    private float shadowOpacity;
    private Color color;
    private CardPreview cardPreview;

    public Card(Type type, MouseManager mouseManager, int x) {
        this.type = type;
        this.mouseManager = mouseManager;
        this.x = x;

        init();
    }

    private void init() {
//        x = 200;
        y = 200;
        w = 100;
        h = 150;
        shadowDepth = 3;
        roundness = 10;
        shadowOpacity = 0.5f;  // 이거 임시로 추가한 거임!

        if (type == Type.KAO) color = Colors.YELLOW;
        else if (type == Type.GARTAR) color = Colors.LIME;
        else if (type == Type.ROTTAR) color = Colors.RED;
        else if (type == Type.ORGAN) color = Colors.BLACK;
        else if (type == Type.SOYAR) color = Colors.SOY;
        else if (type == Type.BAAW) color = Colors.GREEN;
        else if (type == Type.SORVOR) color = Colors.PURPLE;
        else if (type == Type.PHORE) color = Colors.CYAN;
        else if (type == Type.BOVIE) color = Colors.BLUE;
        else if (type == Type.BAINNE) color = Colors.AQUA;
        else if (type == Type.BONAR) color = Colors.COFFEE;

        cardPreview = new CardPreview(this);
    }

    @Override
    public void tick() {
        if (isUnderCursor()) {
            previewing = this;
        }
    }

    private boolean isUnderCursor() {
        int x = mouseManager.getX(), y = mouseManager.getY();
        return this.x < x && x < this.x + w && this.y < y && y < this.y + h;
    }

    private boolean isPreviewing() {
        return previousPreviewing == this && isUnderCursor();
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawShadow(graphics2D, x, y, w, h, shadowDepth, roundness, shadowOpacity);
        graphics2D.setColor(Colors.WHITE);
        graphics2D.fillRoundRect(x, y, w, h, roundness, roundness);

        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x + 10, y + 10, w - 20, h - 20, roundness, roundness);

        if (isPreviewing()) {
            cardPreview.render(graphics);
        }
    }

    private void drawShadow(Graphics2D graphics2D, int x, int y, int w, int h, int shadowDepth, int roundness, float shadowOpacity) {
        graphics2D.setColor(new Color(0, 0, 0, shadowOpacity / shadowDepth));
        for (int i = shadowDepth; i >= 0; i--) {
            graphics2D.fillRoundRect(x - i, y - i, w + i * 2, h + i * 2,
                    roundness + i * 2, roundness + i * 2);
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getW() {
        return w;
    }

    Type getType() {
        return type;
    }
}
