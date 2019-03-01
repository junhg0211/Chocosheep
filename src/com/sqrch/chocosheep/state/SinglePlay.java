package com.sqrch.chocosheep.state;

import com.sqrch.chocosheep.Const;
import com.sqrch.chocosheep.TextFormat;
import com.sqrch.chocosheep.functions.Positioning;
import com.sqrch.chocosheep.game.Set;
import com.sqrch.chocosheep.input.KeyManager;
import com.sqrch.chocosheep.input.MouseManager;
import com.sqrch.chocosheep.root.Display;
import com.sqrch.chocosheep.root.Root;
import com.sqrch.chocosheep.rootobject.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SinglePlay extends State {
    private Root root;
    private Display display;
    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Tablecloth tablecloth;

    private enum Situation { SETTING, PLAYING, RESULT }
    private Situation situation;

    private SettingWindow settingWindow;

    private int leftRounds;
    private Text leftRoundsText;
    private boolean orderNeedToBeSorted;

    private ArrayList<Card> leaveCards;
    private MoneyCard leaveCard;
    private Text leaveCardCount;

    private int money, setLimit;
    private ArrayList<Set> sets;
    private ArrayList<Card> having;
    private int selectedCardIndex;
    private Card selectedCard;

    private MoneyCard moneyCard;
    private Text moneyCardText;

    private int totalMoney;
    private Text resultText;
    private Clickarea clickarea;

    SinglePlay(Root root, Display display, KeyManager keyManager, MouseManager mouseManager) {
        this.root = root;
        this.display = display;
        this.keyManager = keyManager;
        this.mouseManager = mouseManager;

        init();
    }

    private void init() {
        tablecloth = new Tablecloth(display);

        situation = Situation.SETTING;

        settingWindow = new SettingWindow(display, keyManager, root.getLanguageManager());

        having = new ArrayList<>();
        sets = new ArrayList<>();

        moneyCard = new MoneyCard(display.getWidth() - 400, 0);
        moneyCard.setY(display.getHeight() - Card.HEIGHT - 150);

        moneyCardText = new Text(0, display.getHeight() - 320, "", new TextFormat(Const.FONT_PATH, 18, Const.WHITE));

        leftRoundsText = new Text(0, 200, "", new TextFormat(Const.FONT_PATH, 72, Const.WHITE));

        leaveCard = new MoneyCard(0, 0);
        leaveCardCount = new Text(0, 0, "", new TextFormat(Const.FONT_PATH, 72, Const.WHITE));
        readjustLeaveCardText();

        selectedCardIndex = 0;

        totalMoney = 0;
        resultText = new Text(0, 0, "", new TextFormat(Const.FONT_PATH, 72, Const.WHITE));
        clickarea = new Clickarea(0, 0, display.getWidth(), display.getHeight(), mouseManager);
    }

    private int calculateMoney() {
        return (setLimit - 2) * (setLimit + 1 ) / 2 + money;
    }

    private void resetGame() {
        money = 0;
        setLimit = 2;
        leaveCards = Card.getRandomizedDeck(mouseManager, display);
        having = new ArrayList<>();
        sets = new ArrayList<>();
        leftRounds--;
        leftRoundsText.setText(leftRounds + " Left Rounds");
        leftRoundsText.setX(Positioning.center(display.getWidth(), leftRoundsText.getWidth()));
    }

    private int getSecondKeyboardLineToInteger(boolean[] keys) {
        if (keys[KeyEvent.VK_A]) return 0;
        else if (keys[KeyEvent.VK_S]) return 1;
        else if (keys[KeyEvent.VK_D]) return 2;
        else if (keys[KeyEvent.VK_F]) return 3;
        else if (keys[KeyEvent.VK_G]) return 4;
        else if (keys[KeyEvent.VK_H]) return 5;
        else if (keys[KeyEvent.VK_J]) return 6;
        else if (keys[KeyEvent.VK_K]) return 7;
        else return -1;
    }

    private int getThirdKeyboardLineToInteger(boolean[] keys) {
        if (keys[KeyEvent.VK_Q]) return 0;
        else if (keys[KeyEvent.VK_W]) return 1;
        else if (keys[KeyEvent.VK_E]) return 2;
        else if (keys[KeyEvent.VK_R]) return 3;
        else if (keys[KeyEvent.VK_T]) return 4;
        else if (keys[KeyEvent.VK_Y]) return 5;
        else if (keys[KeyEvent.VK_U]) return 6;
        else if (keys[KeyEvent.VK_I]) return 7;
        else return -1;
    }

    private Set getSetByType(Card.Type type) {
        for (Set set : sets) {
            if (set.getType() == type) {
                return set;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private void share() {
        int leaveCardsCount = leaveCards.size() >= 5 ? 5 : leaveCards.size();
        for (int i = 0; i < leaveCardsCount; i++) {
            having.add(leaveCards.get(0));
            leaveCards.remove(0);
        }
        leaveCardCount.setText("" + leaveCards.size());
        readjustLeaveCardText();
    }

    private void readjustLeaveCardText() {
        leaveCard.setX(Positioning.center(display.getWidth(), Card.WIDTH + 40 + leaveCardCount.getWidth()));
        leaveCard.setY(Positioning.center(display.getHeight(), Card.HEIGHT));
        leaveCardCount.setX(display.getWidth() -
                Positioning.center(display.getWidth(), Card.WIDTH + 40 + leaveCardCount.getWidth()) - leaveCardCount.getWidth());
        leaveCardCount.setY((int) (Positioning.center(display.getHeight(), leaveCardCount.getHeight()) +
                        leaveCardCount.getTextFormat().getSize() * 0.75));
    }

    @Override
    public void tick() {
        if (situation == Situation.SETTING) {
            settingWindow.tick();

            if (keyManager.getStartKeys()[KeyEvent.VK_ENTER]) {
                situation = Situation.PLAYING;

                leftRounds = settingWindow.getRounds();
                orderNeedToBeSorted = settingWindow.isOrderNeedToBeSorted();

                resetGame();
            } else if (keyManager.getStartKeys()[KeyEvent.VK_ESCAPE]) {
                root.setState(new Lobby(root, mouseManager, keyManager, display));
            }
        } else if (situation == Situation.PLAYING) {
            boolean[] startKeys = keyManager.getStartKeys();

            if (getSecondKeyboardLineToInteger(startKeys) >= 0) {  // 교환
                int setNumber = setLimit - 1 - getSecondKeyboardLineToInteger(startKeys);

                if (sets.size() >= setNumber + 1 && setNumber >= 0) {
                    int money = sets.get(setNumber).toMoney();
                    if (money > 0) {
                        this.money += money;
                        moneyCardText.setX(moneyCard.getX() +
                                Positioning.center(Card.WIDTH, moneyCardText.getWidth()));
                        moneyCardText.setText("" + this.money);
                        sets.get(setNumber).removeCardByMoney(money);
                        if (sets.get(setNumber).getCount() == 0) {
                            sets.remove(setNumber);
                        }
                    }
                }
            } else if (getThirdKeyboardLineToInteger(startKeys) >= 0) { // 버림
                int setNumber = setLimit - 1 - getThirdKeyboardLineToInteger(startKeys);

                if (sets.size() >= setNumber + 1 && setNumber >= 0) {
                    sets.remove(setNumber);
                }
            }

            if (having.size() == 0) {
                if (leaveCards.size() > 0) {
                    share();
                } else if (sets.isEmpty()) {
                    totalMoney += calculateMoney();
                    if (leftRounds > 0) {
                        resetGame();
                    } else {
                        situation = Situation.RESULT;
                        resultText.setText(String.format("%s : %d", root.getLanguageManager().getString("GatheredMoney"), totalMoney));
                        resultText.setX(Positioning.center(display.getWidth(), resultText.getWidth()));
                        resultText.setY(Positioning.center(display.getHeight(), resultText.getHeight()));
                    }
                }
            } else {
                try {
                    selectedCard = having.get(selectedCardIndex);
                } catch (IndexOutOfBoundsException e) {
                    selectedCardIndex = having.size() - 1;
                    selectedCard = having.get(selectedCardIndex);
                }

                if (startKeys[KeyEvent.VK_Z]) {  // 스택
                    try {
                        Set set = getSetByType(selectedCard.getType());
                        having.remove(selectedCard);
                        set.addCard();
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        if (sets.size() < setLimit) {
                            sets.add(new Set(selectedCard.getType(), 1, display, mouseManager));
                            having.remove(selectedCard);
                        }
                    }
                } else if (startKeys[KeyEvent.VK_X]) {  // 스루
                    having.remove(selectedCard);
                } else if (startKeys[KeyEvent.VK_SHIFT]) {  // 세트 리밋 구매
                    if (setLimit + 1 <= money) {
                        setLimit++;
                        money -= setLimit;
                        moneyCardText.setText("" + money);
                    }
                } else if (startKeys[KeyEvent.VK_C]) {  // 카드 선택 변경 (왼쪽)
                    if (!orderNeedToBeSorted) {
                        selectedCardIndex--;
                        if (selectedCardIndex < 0) {
                            selectedCardIndex = 0;
                        }
                    }
                } else if (startKeys[KeyEvent.VK_V]) {  // 카드 선택 변경 (오른쪽)
                    if (!orderNeedToBeSorted) {
                        selectedCardIndex++;
                        if (selectedCardIndex > having.size() - 1) {
                            selectedCardIndex = having.size() - 1;
                        }
                    }
                }
            }

            for (Set set : sets)
                set.tick();
            for (int i = having.size() - 1; i >= 0; i--)
                having.get(i).tick();
        } else if (situation == Situation.RESULT) {
            if (clickarea.isClicked()) {
                root.setState(new Lobby(root, mouseManager, keyManager, display));
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        tablecloth.render(graphics);

        if (situation == Situation.SETTING) {
            settingWindow.render(graphics);
        } else if (situation == Situation.PLAYING) {
            graphics.setColor(new Color(Const.BLACK.getRed(), Const.BLACK.getGreen(), Const.BLACK.getBlue(), 127));
            for (int i = 0; i < setLimit; i++) {
                graphics.fillRoundRect(display.getWidth() / 2 - (i + 1) * (Card.WIDTH + 10),
                        display.getHeight() - 300, Card.WIDTH, Card.HEIGHT, Card.ROUNDNESS, Card.ROUNDNESS);
            }

            for (int i = having.size() - 1; i >= 0; i--) {
                Card card = having.get(i);
                card.setX(display.getWidth() / 2 + i * 50 + 200);
                card.setY(display.getHeight() - Card.HEIGHT - 150 - (card == selectedCard ? 20 : 0));
                card.render(graphics);
            }
            for (int i = 0; i < sets.size(); i++) {
                sets.get(i).render(graphics, i);
            }

            leftRoundsText.render(graphics);

            leaveCard.render(graphics);
            leaveCardCount.render(graphics);

            if (money > 0) {
                moneyCard.render(graphics);
                moneyCardText.render(graphics);
            }
        } else if (situation == Situation.RESULT) {
            resultText.render(graphics);
        }
    }

    @Override
    public void windowResize() {
        tablecloth.windowResize();
        settingWindow.windowResize();
        readjustLeaveCardText();
        moneyCard.windowResize();
        for (Set set : sets) {
            set.readjustY();
        }
    }
}
