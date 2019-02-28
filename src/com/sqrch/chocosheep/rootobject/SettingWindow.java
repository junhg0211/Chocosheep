package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.root.Display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SettingWindow extends RootObject {
    private Display display;
    private KeyManager keyManager;

    private int rounds, tradeLimit;
    private boolean orderNeedToBeSorted;

    private TextFormat indexNameTextFormat, valueTextFormat;
    private ArrayList<Text> texts;
    private Text roundsText, sortedOrderText, tradeLimitText;

    public SettingWindow(Display display, KeyManager keyManager) {
        this.display = display;
        this.keyManager = keyManager;

        init();
    }

    private void init() {
        rounds = 1;
        orderNeedToBeSorted = true;
        tradeLimit = 4;

        indexNameTextFormat = new TextFormat(Const.FONT_PATH, 36, Const.WHITE);
        valueTextFormat = new TextFormat(Const.FONT_PATH, 144, Const.WHITE);

        texts = new ArrayList<>();

        texts.add(new Text(100, (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "F6D2SE3 T2",
                indexNameTextFormat));
        texts.add(new Text(200 + texts.get(0).getWidth(),
                (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "Z6E3 T2ST4 R44DC6F", indexNameTextFormat));
        texts.add(new Text(300 + texts.get(0).getWidth() + texts.get(1).getWidth(),
                (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "R4F63 W41G6S", indexNameTextFormat));

        roundsText = new Text(100, display.getHeight() - 100, "" + rounds, valueTextFormat);
        sortedOrderText = new Text(200 + texts.get(0).getWidth(), display.getHeight() - 100, "O",
                valueTextFormat);
        tradeLimitText = new Text(300 + texts.get(0).getWidth() + texts.get(1).getWidth(),
                display.getHeight() - 100, "" + tradeLimit, valueTextFormat);
    }

    @Override
    public void tick() {
        boolean[] startKeys = keyManager.getStartKeys();

        if (startKeys[KeyEvent.VK_1] || startKeys[KeyEvent.VK_NUMPAD1]) {
            rounds = 1;
            roundsText.setText("1");
        } else if (startKeys[KeyEvent.VK_2] || startKeys[KeyEvent.VK_NUMPAD2]) {
            rounds = 2;
            roundsText.setText("2");
        } else if (startKeys[KeyEvent.VK_3] || startKeys[KeyEvent.VK_NUMPAD3]) {
            rounds = 3;
            roundsText.setText("3");
        } else if (startKeys[KeyEvent.VK_4] || startKeys[KeyEvent.VK_NUMPAD4]) {
            rounds = 4;
            roundsText.setText("4");
        } else if (startKeys[KeyEvent.VK_5] || startKeys[KeyEvent.VK_NUMPAD5]) {
            rounds = 5;
            roundsText.setText("5");
        } else if (startKeys[KeyEvent.VK_6] || startKeys[KeyEvent.VK_NUMPAD6]) {
            rounds = 6;
            roundsText.setText("6");
        } else if (startKeys[KeyEvent.VK_7] || startKeys[KeyEvent.VK_NUMPAD7]) {
            rounds = 7;
            roundsText.setText("7");
        } else if (startKeys[KeyEvent.VK_8] || startKeys[KeyEvent.VK_NUMPAD8]) {
            rounds = 8;
            roundsText.setText("8");
        } else if (startKeys[KeyEvent.VK_9] || startKeys[KeyEvent.VK_NUMPAD9]) {
            rounds = 9;
            roundsText.setText("9");
        } else if (startKeys[KeyEvent.VK_0] || startKeys[KeyEvent.VK_NUMPAD0]) {
            orderNeedToBeSorted = !orderNeedToBeSorted;
            sortedOrderText.setText(orderNeedToBeSorted ? "O" : "X");
        } else if (startKeys[KeyEvent.VK_Q]) {
            tradeLimit = 1;
            tradeLimitText.setText("1");
        } else if (startKeys[KeyEvent.VK_W]) {
            tradeLimit = 2;
            tradeLimitText.setText("2");
        } else if (startKeys[KeyEvent.VK_E]) {
            tradeLimit = 3;
            tradeLimitText.setText("3");
        } else if (startKeys[KeyEvent.VK_R]) {
            tradeLimit = 4;
            tradeLimitText.setText("4");
        } else if (startKeys[KeyEvent.VK_T]) {
            tradeLimit = 5;
            tradeLimitText.setText("5");
        } else if (startKeys[KeyEvent.VK_Y]) {
            tradeLimit = 8;
            tradeLimitText.setText("8");
        } else if (startKeys[KeyEvent.VK_U]) {
            tradeLimit = 10;
            tradeLimitText.setText("10");
        } else if (startKeys[KeyEvent.VK_I]) {
            tradeLimit = 12;
            tradeLimitText.setText("12");
        } else if (startKeys[KeyEvent.VK_O]) {
            tradeLimit = 15;
            tradeLimitText.setText("15");
        } else if (startKeys[KeyEvent.VK_P]) {
            tradeLimit = 16;
            tradeLimitText.setText("16");
        } else if (startKeys[KeyEvent.VK_OPEN_BRACKET]) {
            tradeLimit = 20;
            tradeLimitText.setText("20");
        } else if (startKeys[KeyEvent.VK_CLOSE_BRACKET]) {
            tradeLimit = Integer.MAX_VALUE;
            tradeLimitText.setText("D4QTD3A");
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 127));
        graphics.fillRoundRect(50, 50, display.getWidth() - 100, display.getHeight() - 100, 50, 50);
        for (Text text : texts) {
            text.render(graphics);
        }
        roundsText.render(graphics);
        sortedOrderText.render(graphics);
        tradeLimitText.render(graphics);
    }

    @Override
    public void windowResize() {
        texts = new ArrayList<>();

        texts.add(new Text(100, (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "라운드 수",
                indexNameTextFormat));
        texts.add(new Text(200 + texts.get(0).getWidth(),
                (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "카드 순서 경찰", indexNameTextFormat));
        texts.add(new Text(300 + texts.get(0).getWidth() + texts.get(1).getWidth(),
                (int) (display.getHeight() - 70 - valueTextFormat.getSize()), "거래 제한", indexNameTextFormat));

        roundsText = new Text(100, display.getHeight() - 100, "" + rounds, valueTextFormat);
        sortedOrderText = new Text(200 + texts.get(0).getWidth(), display.getHeight() - 100, "O",
                valueTextFormat);
        tradeLimitText = new Text(300 + texts.get(0).getWidth() + texts.get(1).getWidth(),
                display.getHeight() - 100, "" + tradeLimit, valueTextFormat);
    }

    public int getRounds() {
        return rounds;
    }

    public boolean isOrderNeedToBeSorted() {
        return orderNeedToBeSorted;
    }
}
