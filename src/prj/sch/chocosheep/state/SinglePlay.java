package prj.sch.chocosheep.state;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;
import prj.sch.chocosheep.game.Set;
import prj.sch.chocosheep.input.KeyManager;
import prj.sch.chocosheep.input.MouseManager;
import prj.sch.chocosheep.root.Display;
import prj.sch.chocosheep.root.Root;
import prj.sch.chocosheep.rootobject.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class SinglePlay extends State {
    private Root root;
    private Display display;
    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Tablecloth tablecloth;

    private enum Situation {
        SETTING, PLAYING
    }
    private Situation situation;

    private SettingWindow settingWindow;

    private int rounds, tradeLimit;
    private boolean sortedOrder;

    private ArrayList<Card> leaveCards;

    private int money, setLimit;
    private ArrayList<Set> sets;
    private ArrayList<Card> having;
    private Card selectedCard;

    private MoneyCard moneyCard;
    private Text moneyCardText;

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

        settingWindow = new SettingWindow(display, keyManager);

        moneyCard = new MoneyCard(display.getWidth() - 400, 0);
        moneyCard.setY(display.getHeight() - Card.HEIGHT - 150);

        moneyCardText = new Text(0, display.getHeight() - 320, "",
                new TextFormat(Const.FONT_PATH, 18, Const.WHITE));
    }

    @Override
    public void tick() {
        if (situation == Situation.SETTING) {
            settingWindow.tick();

            if (keyManager.getStartKeys()[KeyEvent.VK_ENTER]) {
                rounds = settingWindow.getRounds();
                sortedOrder = settingWindow.isSortedOrder();
                tradeLimit = settingWindow.getTradeLimit();
                situation = Situation.PLAYING;
                leaveCards = Card.getRandomizedDeck(mouseManager);

                setLimit = 2;
                having = new ArrayList<>();
                sets = new ArrayList<>();
                money = 0;
            }
        } else if (situation == Situation.PLAYING) {
            for (Set set : sets) {
                set.tick();
            }
            for (int i = having.size() - 1; i >= 0; i--) {
                having.get(i).tick();
            }

            if (having.size() == 0) {
                share();
            } else {
                selectedCard = having.get(0);

                boolean[] startKeys = keyManager.getStartKeys();

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
                } else if (getSecondKeyboardLineToInteger(startKeys) >= 0) {  // 교환
                    int setNumber = setLimit - 1 - getSecondKeyboardLineToInteger(startKeys);

                    if (sets.size() >= setNumber + 1 && setNumber >= 0) {
                        int money = sets.get(setNumber).toMoney();
                        if (money > 0) {
                            this.money += money;
                            moneyCardText.setX(moneyCard.getX() +
                                    Positioning.center(Card.WIDTH, moneyCardText.getWidth()));
                            moneyCardText.setText("" + this.money);
                            sets.get(setNumber).removeCardByMoney(money);
                            System.out.println(money);
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
            }
        }
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
        for (int i = 0; i < (leaveCards.size() >= 5 ? 5 : leaveCards.size()); i++) {
            having.add(leaveCards.get(0));
            leaveCards.remove(0);
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
                card.setY(display.getHeight() - Card.HEIGHT - 150);
                card.render(graphics);
            }
            for (int i = 0; i < sets.size(); i++) {
                sets.get(i).render(graphics, i);
            }
            if (money > 0) {
                moneyCard.render(graphics);
                moneyCardText.render(graphics);
            }
        }
    }

    @Override
    public void windowResize() {
        tablecloth.windowResize();
        settingWindow.windowResize();
    }
}
