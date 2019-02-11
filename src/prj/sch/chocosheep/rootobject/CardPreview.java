package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Colors;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;

import java.awt.*;

public class CardPreview extends RootObject {
    private Card card;

    private int x, y, w, h;
    private Color color;
    private float opacity;

    private Text count;
    private Text[] prise;

    CardPreview(Card card) {
        this.card = card;

        init();
    }

    private void init() {
        h = 50;
        w = card.getW() + 100;
        x = card.getX() - 50;
        y = card.getY() - h - 10;
        color = Colors.BLACK;
        opacity = 0.8f;

        TextFormat countTextFormat = new TextFormat("./res/font/BMJUA_ttf.ttf", 36, Colors.WHITE);
        TextFormat priseTextFormat = new TextFormat("./res/font/BMJUA_ttf.ttf", 15, Colors.WHITE);

        String countText;
        String[] priseText;
        if (card.getType() == Card.Type.KAO) {
            countText = "4";
            priseText = new String[] {"X", "2", "3", "4"};
        } else if (card.getType() == Card.Type.GARTAR) {
            countText = "6";
            priseText = new String[] {"X", "2", "3", "X"};
        } else if (card.getType() == Card.Type.ROTTAR) {
            countText = "8";
            priseText = new String[] {"2", "3", "4", "5"};
        } else if (card.getType() == Card.Type.ORGAN) {
            countText = "10";
            priseText = new String[] {"2", "4", "5", "6"};
        } else if (card.getType() == Card.Type.SOYAR) {
            countText = "12";
            priseText = new String[] {"2", "4", "6", "7"};
        } else if (card.getType() == Card.Type.BAAW) {
            countText = "14";
            priseText = new String[] {"3", "5", "7", "7"};
        } else if (card.getType() == Card.Type.SORVOR) {
            countText = "16";
            priseText = new String[] {"3", "5", "7", "8"};
        } else if (card.getType() == Card.Type.PHORE) {
            countText = "18";
            priseText = new String[] {"3", "6", "8", "9"};
        } else if (card.getType() == Card.Type.BOVIE) {
            countText = "20";
            priseText = new String[] {"4", "6", "8", "10"};
        } else if (card.getType() == Card.Type.BAINNE) {
            countText = "22";
            priseText = new String[] {"4", "7", "9", "11"};
        } else {
            countText = "24";
            priseText = new String[] {"4", "7", "10", "12"};
        }

        count = new Text(0, 0, countText, countTextFormat);
        count.setX(x + 7);
        count.setY(card.getY() + 7 + Positioning.center(h, count.getHeight()) - count.getHeight());
        prise = new Text[4];

        int textAreaWidth = (int) ((w - 21 - count.getWidth()) / 4.5);
        int priseY = y + h - 21;
        for (int i = 0; i < 4; i++) {
            prise[i] = new Text(0, priseY, priseText[i], priseTextFormat);
            prise[i].setX(x + textAreaWidth * i + prise[i].getWidth() + count.getWidth() + 21);
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, opacity));
        graphics.fillRoundRect(x, y, w, h, 10, 10);

        count.render(graphics);
        for (int i = 0; i < 4; i++) {
            prise[i].render(graphics);
        }
    }
}
