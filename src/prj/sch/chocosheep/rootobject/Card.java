package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.game.Set;
import prj.sch.chocosheep.input.MouseManager;

import java.awt.*;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Card extends RootObject {
    public static int WIDTH = 100;
    public static int HEIGHT = 150;
    static int SHADOW_DEPTH = 3;
    public static int ROUNDNESS = 10;
    static int BORDER_WIDTH = 10;
    static float SHADOW_OPACITY = 0.5f;

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

    private Text count;
    private Text prise1, prise2, prise3, prise4;

    public Card(Type type, MouseManager mouseManager) {
        this.type = type;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        TextFormat cardWhiteTextFormat = new TextFormat(Const.FONT_PATH, 24, Const.WHITE);
        TextFormat cardBlackTextFormat = new TextFormat(Const.FONT_PATH, 24, Const.BLACK);
        int[] list;
        if (type == Type.KAO) {
            color = Const.YELLOW;
            count = new Text(0, 0, "4", cardBlackTextFormat);
            list = Set.KAO;
        }
        else if (type == Type.GARTAR) {
            color = Const.LIME;
            count = new Text(0, 0, "6", cardBlackTextFormat);
            list = Set.GARTAR;
        }
        else if (type == Type.ROTTAR) {
            color = Const.RED;
            count = new Text(0, 0, "8", cardWhiteTextFormat);
            list = Set.ROTTAR;
        }
        else if (type == Type.ORGAN) {
            color = Const.BLACK;
            count = new Text(0, 0, "10", cardWhiteTextFormat);
            list = Set.ORGAN;
        }
        else if (type == Type.SOYAR) {
            color = Const.SOY;
            count = new Text(0, 0, "12", cardBlackTextFormat);
            list = Set.SOYAR;
        }
        else if (type == Type.BAAW) {
            color = Const.GREEN;
            count = new Text(0, 0, "14", cardWhiteTextFormat);
            list = Set.BAAW;
        }
        else if (type == Type.SORVOR) {
            color = Const.PURPLE;
            count = new Text(0, 0, "16", cardWhiteTextFormat);
            list = Set.SORVOR;
        }
        else if (type == Type.PHORE) {
            color = Const.CYAN;
            count = new Text(0, 0, "18", cardWhiteTextFormat);
            list = Set.PHORE;
        }
        else if (type == Type.BOVIE) {
            color = Const.BLUE;
            count = new Text(0, 0, "20", cardWhiteTextFormat);
            list = Set.BOVIE;
        }
        else if (type == Type.BAINNE) {
            color = Const.AQUA;
            count = new Text(0, 0, "22", cardWhiteTextFormat);
            list = Set.BAINNE;
        }
        else if (type == Type.BONAR) {
            color = Const.COFFEE;
            count = new Text(0, 0, "24", cardWhiteTextFormat);
            list = Set.BONAR;
        } else {
            return;
        }

        TextFormat priseTextFormat = new TextFormat(Const.FONT_PATH, 16, count.getTextFormat().getColor());
        prise1 = new Text(0, 0, "" + list[0], priseTextFormat);
        prise2 = new Text(0, 0, "" + list[1], priseTextFormat);
        prise3 = new Text(0, 0, "" + list[2], priseTextFormat);
        prise4 = new Text(0, 0, "" + list[3], priseTextFormat);

        adjustTextPosition();

        cardPreview = new CardPreview(this);
    }

    private void adjustCountXPosition() {
        count.setX(x + WIDTH - count.getWidth() - BORDER_WIDTH - BORDER_WIDTH);

        prise1.setX(x + BORDER_WIDTH * 2);
        prise2.setX(x + BORDER_WIDTH * 2);
        prise3.setX(x + BORDER_WIDTH * 2);
        prise4.setX(x + BORDER_WIDTH * 2);
    }

    private void adjustCountYPosition() {
        count.setY(y + BORDER_WIDTH * 2 + count.getHeight());

        prise1.setY(y + (HEIGHT - BORDER_WIDTH * 2) / 4);
        prise2.setY(y + (HEIGHT - BORDER_WIDTH * 2) / 4 * 2);
        prise3.setY(y + (HEIGHT - BORDER_WIDTH * 2) / 4 * 3);
        prise4.setY(y + (HEIGHT - BORDER_WIDTH * 2) / 4 * 4);
    }

    private void adjustTextPosition() {
        adjustCountXPosition();
        adjustCountYPosition();
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
        graphics2D.setColor(count.getTextFormat().getColor());
        count.render(graphics);
        prise1.render(graphics);
        prise2.render(graphics);
        prise3.render(graphics);
        prise4.render(graphics);

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
        adjustCountXPosition();
    }

    public void setY(int y) {
        this.y = y;
        cardPreview = new CardPreview(this);
        adjustCountYPosition();
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
