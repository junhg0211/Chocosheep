package com.sqrch.chocosheep.rootobject;

import com.sqrch.chocosheep.root.Display;

import java.awt.*;

public class Tablecloth extends RootObject {
    private static Color LINE = new Color(129, 41, 50);
    private static Color BACKGROUND = new Color(75, 0, 15);

    private Display display;

    private int width, height;
    private int[] xs, xPoly, yPoly;

    public Tablecloth(Display display) {
        this.display = display;

        init();
    }

    private void init() {
        width = display.getWidth();
        height = display.getHeight();

        xs = new int[6];
        if (width > height) {
            xs[0] = (int) (Math.max(width, height) * 0.05);
            xs[1] = (int) (Math.max(width, height) * 0.07);
            xs[2] = (int) (Math.max(width, height) * 0.09);
            xs[3] = (int) (Math.max(width, height) * 0.91);
            xs[4] = (int) (Math.max(width, height) * 0.93);
            xs[5] = (int) (Math.max(width, height) * 0.95);
        } else {
            xs[0] = (int) (Math.min(width, height) * 0.05);
            xs[1] = (int) (Math.min(width, height) * 0.07);
            xs[2] = (int) (Math.min(width, height) * 0.09);
            xs[3] = (int) (Math.min(width, height) * 0.91);
            xs[4] = (int) (Math.min(width, height) * 0.93);
            xs[5] = (int) (Math.min(width, height) * 0.95);
        }

        xPoly = new int[4];
        xPoly[0] = (int) (-width * 0.05);
        xPoly[1] = width / 2;
        xPoly[2] = (int) (width * 1.05);
        xPoly[3] = width / 2;

        yPoly = new int[4];
        yPoly[0] = height / 2;
        yPoly[1] = (int) (height / 2 - width * 0.55);
        yPoly[2] = height / 2;
        yPoly[3] = (int) (height / 2 + width * 0.55);
    }

    private int[] getYs(int x) {
        int delta = Math.abs(width / 2 - x), centerY = height / 2;
        return new int[] {centerY + delta, centerY - delta};
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(BACKGROUND);
        graphics2D.fillPolygon(xPoly, yPoly, xPoly.length);

        graphics2D.setColor(LINE);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke((float) (width / 200)));
        for (int x : xs) {
            for (int y : getYs(x)) {
                graphics2D.drawLine(x, height / 2, width / 2, y);
            }
        }
    }

    @Override
    public void windowResize() {
        init();
    }
}
