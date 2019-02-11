package prj.sch.chocosheep.game;

import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.rootobject.Card;

import java.awt.*;
import java.util.ArrayList;

public class Set {
    private Card.Type type;
    private int count;
    private Display display;
    private MouseManager mouseManager;

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

        readjustX();
    }

    public void tick() {
        for (Card card : cards) {
            card.tick();
        }
    }

    public void render(Graphics graphics, int setNumber) {
        int x = display.getWidth() / 2 - (setNumber + 1) * (Card.WIDTH + 10);
        for (Card card : cards) {
            card.setX(x);
            card.render(graphics);
        }
    }

    public void addCard() {
        count += 1;
        readjustX();
    }

    private void readjustX() {
        for (int i = count - 1; i >= 0; i--) {
            Card card = new Card(type, mouseManager);
            card.setY(display.getHeight() - 400 + i * 30);
            cards.add(card);
        }
    }

    public Card.Type getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
