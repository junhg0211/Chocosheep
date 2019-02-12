package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Const;
import prj.sch.chocosheep.TextFormat;
import prj.sch.chocosheep.functions.Positioning;

import java.awt.*;

import static prj.sch.chocosheep.rootobject.Card.*;

public class MoneyCard extends RootObject {
    private int x, y;

    private Text backTerroText;

    public MoneyCard(int x, int y) {
        this.x = x;
        this.y = y;

        init();
    }

    private void init() {
        backTerroText = new Text(0, 0, "T", new TextFormat(Const.FONT_PATH, WIDTH / 2f, Const.WHITE));

        backTerroText.setX(x + Positioning.center(WIDTH, backTerroText.getWidth()));
        backTerroText.setY(y + Positioning.center(HEIGHT, backTerroText.getHeight()) + backTerroText.getHeight() - 10);
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Card.drawShadow(graphics2D, x, y, WIDTH, HEIGHT, SHADOW_DEPTH, ROUNDNESS, SHADOW_OPACITY);
        graphics2D.setColor(Const.WHITE);
        graphics2D.fillRoundRect(x, y, WIDTH, HEIGHT, ROUNDNESS, ROUNDNESS);

        graphics2D.setColor(Const.BLACK);
        graphics2D.fillOval(x + Positioning.center(WIDTH, WIDTH - BORDER_WIDTH * 2),
                y + Positioning.center(HEIGHT, WIDTH - BORDER_WIDTH * 2), WIDTH - BORDER_WIDTH * 2,
                WIDTH - BORDER_WIDTH * 2);
        backTerroText.render(graphics);
    }

    public void setX(int x) {
        this.x = x;
        backTerroText.setX(x + Positioning.center(WIDTH, backTerroText.getWidth()));
    }

    public void setY(int y) {
        this.y = y;
        backTerroText.setY(y + Positioning.center(HEIGHT, backTerroText.getHeight()) + backTerroText.getHeight() - 10);
    }

    public int getX() {
        return x;
    }
}
