package prj.sch.chocosheep.input;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseManager implements MouseInputListener {
    private final int KEY_COUNT = 3;

    private int x, y;
    private boolean[] click, previousClick, startClick, endClick;
    private int clickStartX, clickStartY;

    public MouseManager() {
        init();
    }

    private void init() {
        click = new boolean[KEY_COUNT];
        previousClick = new boolean[KEY_COUNT];
        startClick = new boolean[KEY_COUNT];
        endClick = new boolean[KEY_COUNT];
    }

    public void tick() {
        for (int i = 0; i < click.length; i++) {
            startClick[i] = !previousClick[i] && click[i];
            endClick[i] = previousClick[i] && !click[i];
        }

        previousClick = click.clone();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        clickStartX = e.getX();
        clickStartY = e.getY();

        click[e.getButton() - 1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        click[e.getButton() - 1] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean[] getClick() {
        return click;
    }

    public boolean getLeftClick() {
        return click[0];
    }

    public boolean[] getStartClick() {
        return startClick;
    }

    public boolean[] getEndClick() {
        return endClick;
    }

    public boolean getLeftEndClick() {
        return endClick[0];
    }

    public int getClickStartX() {
        return clickStartX;
    }

    public int getClickStartY() {
        return clickStartY;
    }
}
