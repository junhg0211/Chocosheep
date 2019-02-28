package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.functions.Positioning;
import com.sqrch.chocosheep.game.Set;

import java.awt.*;

public class CardPreview extends RootObject {
    private Card card;

    private int x, y, w, h;
    private Color color;
    private float opacity;

    private Text count, name;
    private Text[] prise;

    CardPreview(Card card) {
        this.card = card;

        init();
    }

    private void init() {
        h = 120;
        w = Card.WIDTH + 100;
        x = card.getX() - 50;
        y = card.getY() - h - 10;
        color = Const.BLACK;
        opacity = 0.8f;

        TextFormat countTextFormat = new TextFormat(Const.FONT_PATH, 36, Const.WHITE);
        TextFormat priseTextFormat = new TextFormat(Const.FONT_PATH, 15, Const.WHITE);

        String countText;
        int[] list;
        if (card.getType() == Card.Type.KAO) {
            countText = "4";
            list = Set.KAO;
        } else if (card.getType() == Card.Type.GARTAR) {
            countText = "6";
            list = Set.GARTAR;
        } else if (card.getType() == Card.Type.ROTTAR) {
            countText = "8";
            list = Set.ROTTAR;
        } else if (card.getType() == Card.Type.ORGAN) {
            countText = "10";
            list = Set.ORGAN;
        } else if (card.getType() == Card.Type.SOYAR) {
            countText = "12";
            list = Set.SOYAR;
        } else if (card.getType() == Card.Type.BAAW) {
            countText = "14";
            list = Set.BAAW;
        } else if (card.getType() == Card.Type.SORVOR) {
            countText = "16";
            list = Set.SORVOR;
        } else if (card.getType() == Card.Type.PHORE) {
            countText = "18";
            list = Set.PHORE;
        } else if (card.getType() == Card.Type.BOVIE) {
            countText = "20";
            list = Set.BOVIE;
        } else if (card.getType() == Card.Type.BAINNE) {
            countText = "22";
            list = Set.BAINNE;
        } else {
            countText = "24";
            list = Set.BONAR;
        }

        String[] priseText = {"" + list[0], "" + list[1], "" + list[2], "" + list[3]};

        count = new Text(0, 0, countText, countTextFormat);
        count.setX(x + 15);
        count.setY(y + h - 21);
        prise = new Text[4];

        name = new Text(0, 0, "" + card.getType(), countTextFormat);
        name.setX(x + Positioning.center(w, name.getWidth()));
        name.setY(y + 15 + name.getHeight());

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

        name.render(graphics);
        count.render(graphics);
        for (int i = 0; i < 4; i++) {
            prise[i].render(graphics);
        }
    }
}
