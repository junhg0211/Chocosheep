package com.sqrch.chocosheep.game;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.functions.Positioning;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Display;
import com.sqrch.chocosheep.rootobject.Card;
import com.sqrch.chocosheep.rootobject.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Set {
    public static int[] KAO = {0, 2, 3, 4};
    public static int[] GARTAR = {0, 2, 3, 0};
    public static int[] ROTTAR = {2, 3, 4, 5};
    public static int[] ORGAN = {2, 4, 5, 6};
    public static int[] SOYAR = {2, 4, 6, 7};
    public static int[] BAAW = {3, 5, 6, 7};
    public static int[] SORVOR = {3, 5, 7, 8};
    public static int[] PHORE = {3, 6, 8, 9};
    public static int[] BOVIE = {4, 6, 8, 10};
    public static int[] BAINNE = {4, 7, 9, 11};
    public static int[] BONAR = {4, 7, 10, 12};

    private Card.Type type;
    private int count;
    private Display display;
    private MouseManager mouseManager;
    private Text countText;

    private ArrayList<Card> cards;

    public Set(Card.Type type, int count, Display display, MouseManager mouseManager) {
        this.type = type;
        this.count = count;
        this.display = display;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        cards = new ArrayList<>();

        TextFormat textFormat = new TextFormat(Const.FONT_PATH, 18, Const.WHITE);
        countText = new Text(0, display.getHeight() - 320, "" + count, textFormat);

        readjustY();
    }

    private static int[] getListByType(Card.Type type) {
        if (type == Card.Type.KAO) return KAO;
        else if (type == Card.Type.GARTAR) return GARTAR;
        else if (type == Card.Type.ROTTAR) return ROTTAR;
        else if (type == Card.Type.ORGAN) return ORGAN;
        else if (type == Card.Type.SOYAR) return SOYAR;
        else if (type == Card.Type.BAAW) return BAAW;
        else if (type == Card.Type.SORVOR) return SORVOR;
        else if (type == Card.Type.PHORE) return PHORE;
        else if (type == Card.Type.BOVIE) return BOVIE;
        else if (type == Card.Type.BAINNE) return BAINNE;
        else if (type == Card.Type.BONAR) return BONAR;
        else return null;
    }

    public void removeCardByMoney(int money) {
        if (getCardCountByMoney(money) > 0) {
            try {
                cards.subList(0, getCardCountByMoney(money)).clear();
            } catch (IndexOutOfBoundsException e) {
                cards.clear();
                count = 0;
                return;
            }
        }
        count -= getCardCountByMoney(money);
        readjustY();
        countText.setText("" + count);
    }

    private int getCardCountByMoney(int money) {
        int[] list = getListByType(type);

        if (list == null) {
            return 0;
        }

        return list[money - 1];
    }

    public int toMoney() {
        int[] list = getListByType(type);

        if (list == null) {
            return 0;
        }

        if (type == Card.Type.KAO) {
            if (count >= list[3]) return 4;
            else if (count >= list[2]) return 3;
            else if (count >= list[1]) return 2;
        } else if (type == Card.Type.GARTAR) {
            if (count >= list[2]) return 3;
            else if (count >= list[1]) return 2;
            else if (count >= list[0]) return 1;
        } else {
            if (count >= list[3]) return 4;
            else if (count >= list[2]) return 3;
            else if (count >= list[1]) return 2;
            else if (count >= list[0]) return 1;
        }

        return 0;
    }

    public void tick() {
        for (Card card : cards) {
            card.tick();
        }
    }

    public void render(Graphics graphics, int setNumber) {
        int x = display.getWidth() / 2 - (setNumber + 1) * (Card.WIDTH + 10);
        countText.setX(x + Positioning.center(Card.WIDTH, countText.getWidth()));
        countText.render(graphics);
        try {
            for (Card card : cards) {
                card.setX(x);
                card.render(graphics);
            }
        } catch (ConcurrentModificationException ignored) {}
    }

    public void addCard() {
        count += 1;
        readjustY();
        countText.setText("" + count);
    }

    public void readjustY() {
        cards.clear();
        for (int i = count - 1; i >= 0; i--) {
            Card card = new Card(type, mouseManager, display);
            card.setY(display.getHeight() - 300 + i * 30);
            cards.add(card);
        }
        countText.setY(display.getHeight() - 320);
    }

    public Card.Type getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
