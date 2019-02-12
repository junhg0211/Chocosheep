package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;
import prj.sch.chocosheep.input.MouseManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Card extends RootObject {
    public static int WIDTH = 100;
    public static int HEIGHT = 150;
    public static int SHADOW_DEPTH = 3;
    public static int ROUNDNESS = 10;
    public static int BORDER_WIDTH = 10;
    public static float SHADOW_OPACITY = 0.5f;

    private static ArrayList<Card> getSortedDeck(MouseManager mouseManager) {
        ArrayList<Card> deck = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            deck.add(new Card(Type.KAO, mouseManager));
        } for (int i = 0; i < 6; i++) {
            deck.add(new Card(Type.GARTAR, mouseManager));
        } for (int i = 0; i < 8; i++) {
            deck.add(new Card(Type.ROTTAR, mouseManager));
        } for (int i = 0; i < 10; i++) {
            deck.add(new Card(Type.ORGAN, mouseManager));
        } for (int i = 0; i < 12; i++) {
            deck.add(new Card(Type.SOYAR, mouseManager));
        } for (int i = 0; i < 14; i++) {
            deck.add(new Card(Type.BAAW, mouseManager));
        } for (int i = 0; i < 16; i++) {
            deck.add(new Card(Type.SORVOR, mouseManager));
        } for (int i = 0; i < 18; i++) {
            deck.add(new Card(Type.PHORE, mouseManager));
        } for (int i = 0; i < 20; i++) {
            deck.add(new Card(Type.BOVIE, mouseManager));
        } for (int i = 0; i < 22; i++) {
            deck.add(new Card(Type.BAINNE, mouseManager));
        } for (int i = 0; i < 24; i++) {
            deck.add(new Card(Type.BONAR, mouseManager));
        }

        return deck;
    }

    public static ArrayList<Card> getRandomizedDeck(MouseManager mouseManager) {
        ArrayList<Card> deck = getSortedDeck(mouseManager);

        Collections.shuffle(deck);

        return deck;
    }

    public static Card previewing, previousPreviewing;

    public enum Type { KAO, GARTAR, ROTTAR, ORGAN, SOYAR, BAAW, SORVOR, PHORE, BOVIE, BAINNE, BONAR, NULL }

    private Type type;
    private MouseManager mouseManager;

    private int x, y;
    private Color color;
    private CardPreview cardPreview;

    private Text text;

    public Card(Type type, MouseManager mouseManager) {
        this.type = type;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        TextFormat cardWhiteTextFormat = new TextFormat(Const.FONT_PATH, 24, Const.WHITE);
        TextFormat cardBlackTextFormat = new TextFormat(Const.FONT_PATH, 24, Const.BLACK);
        if (type == Type.KAO) {
            color = Const.YELLOW;
            text = new Text(0, 0, "4", cardBlackTextFormat);
        }
        else if (type == Type.GARTAR) {
            color = Const.LIME;
            text = new Text(0, 0, "6", cardBlackTextFormat);
        }
        else if (type == Type.ROTTAR) {
            color = Const.RED;
            text = new Text(0, 0, "8", cardWhiteTextFormat);
        }
        else if (type == Type.ORGAN) {
            color = Const.BLACK;
            text = new Text(0, 0, "10", cardWhiteTextFormat);
        }
        else if (type == Type.SOYAR) {
            color = Const.SOY;
            text = new Text(0, 0, "12", cardBlackTextFormat);
        }
        else if (type == Type.BAAW) {
            color = Const.GREEN;
            text = new Text(0, 0, "14", cardWhiteTextFormat);
        }
        else if (type == Type.SORVOR) {
            color = Const.PURPLE;
            text = new Text(0, 0, "16", cardWhiteTextFormat);
        }
        else if (type == Type.PHORE) {
            color = Const.CYAN;
            text = new Text(0, 0, "18", cardWhiteTextFormat);
        }
        else if (type == Type.BOVIE) {
            color = Const.BLUE;
            text = new Text(0, 0, "20", cardWhiteTextFormat);
        }
        else if (type == Type.BAINNE) {
            color = Const.AQUA;
            text = new Text(0, 0, "22", cardWhiteTextFormat);
        }
        else if (type == Type.BONAR) {
            color = Const.COFFEE;
            text = new Text(0, 0, "24", cardWhiteTextFormat);
        }
        adjustTextPosition();

        cardPreview = new CardPreview(this);
    }

    private void adjustTextXPosition() {
        text.setX(x + WIDTH - text.getWidth() - BORDER_WIDTH - BORDER_WIDTH);
    }

    private void adjustTextYPosition() {
        text.setY(y + BORDER_WIDTH * 2 + text.getHeight());
    }

    private void adjustTextPosition() {
        adjustTextXPosition();
        adjustTextYPosition();
    }

    @Override
    public void tick() {
        if (isUnderCursor()) {
            previewing = this;
        }
    }

    private boolean isUnderCursor() {
        int x = mouseManager.getX(), y = mouseManager.getY();
        return this.x < x && x < this.x + WIDTH && this.y < y && y < this.y + HEIGHT;
    }

    private boolean isPreviewing() {
        return previousPreviewing == this && isUnderCursor();
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawShadow(graphics2D, x, y, WIDTH, HEIGHT, SHADOW_DEPTH, ROUNDNESS, SHADOW_OPACITY);
        graphics2D.setColor(Const.WHITE);
        graphics2D.fillRoundRect(x, y, WIDTH, HEIGHT, ROUNDNESS, ROUNDNESS);

        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x + BORDER_WIDTH, y + BORDER_WIDTH,
                WIDTH - BORDER_WIDTH * 2, HEIGHT - BORDER_WIDTH * 2, ROUNDNESS, ROUNDNESS);
        graphics2D.setColor(text.getTextFormat().getColor());
        text.render(graphics);

        if (isPreviewing()) {
            cardPreview.render(graphics);
        }
    }

    static void drawShadow(Graphics2D graphics2D, int x, int y, int w, int h, int shadowDepth, int roundness, float shadowOpacity) {
        graphics2D.setColor(new Color(0, 0, 0, shadowOpacity / shadowDepth));
        for (int i = shadowDepth; i >= 0; i--) {
            graphics2D.fillRoundRect(x - i, y - i, w + i * 2, h + i * 2,
                    roundness + i * 2, roundness + i * 2);
        }
    }

    public void setX(int x) {
        this.x = x;
        cardPreview = new CardPreview(this);
        adjustTextXPosition();
    }

    public void setY(int y) {
        this.y = y;
        cardPreview = new CardPreview(this);
        adjustTextYPosition();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
}
