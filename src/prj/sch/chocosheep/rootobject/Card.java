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
    private static int HEIGHT = 150;
    private static int SHADOW_DEPTH = 3;
    private static int ROUNDNESS = 10;
    private static float SHADOW_OPACITY = 0.5f;

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

    private boolean back;
    private Text backTerroText;

    public Card(Type type, MouseManager mouseManager) {
        this.type = type;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        if (type == Type.KAO) color = Const.YELLOW;
        else if (type == Type.GARTAR) color = Const.LIME;
        else if (type == Type.ROTTAR) color = Const.RED;
        else if (type == Type.ORGAN) color = Const.BLACK;
        else if (type == Type.SOYAR) color = Const.SOY;
        else if (type == Type.BAAW) color = Const.GREEN;
        else if (type == Type.SORVOR) color = Const.PURPLE;
        else if (type == Type.PHORE) color = Const.CYAN;
        else if (type == Type.BOVIE) color = Const.BLUE;
        else if (type == Type.BAINNE) color = Const.AQUA;
        else if (type == Type.BONAR) color = Const.COFFEE;

        cardPreview = new CardPreview(this);

        back = false;
        backTerroText = new Text(0, 0, "T", new TextFormat(Const.FONT_PATH, WIDTH / 2f, Const.WHITE));
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

        if (back) {
            graphics2D.setColor(Const.BLACK);
            graphics2D.fillOval(x + Positioning.center(WIDTH, WIDTH - 20), y + Positioning.center(HEIGHT, WIDTH - 20), WIDTH - 20, WIDTH - 20);
            backTerroText.render(graphics);
        } else {
            graphics2D.setColor(color);
            graphics2D.fillRoundRect(x + 10, y + 10, WIDTH - 20, HEIGHT - 20, ROUNDNESS, ROUNDNESS);

            if (isPreviewing()) {
                cardPreview.render(graphics);
            }
        }
    }

    private void drawShadow(Graphics2D graphics2D, int x, int y, int w, int h, int shadowDepth, int roundness, float shadowOpacity) {
        graphics2D.setColor(new Color(0, 0, 0, shadowOpacity / shadowDepth));
        for (int i = shadowDepth; i >= 0; i--) {
            graphics2D.fillRoundRect(x - i, y - i, w + i * 2, h + i * 2,
                    roundness + i * 2, roundness + i * 2);
        }
    }

    public void setX(int x) {
        this.x = x;
        cardPreview = new CardPreview(this);
        backTerroText.setX(x + Positioning.center(WIDTH, backTerroText.getWidth()));
    }

    public void setY(int y) {
        this.y = y;
        cardPreview = new CardPreview(this);
        backTerroText.setY(y + Positioning.center(HEIGHT, backTerroText.getHeight()) + backTerroText.getHeight() - 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public Type getType() {
        return type;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
