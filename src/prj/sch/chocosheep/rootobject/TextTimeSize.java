package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.functions.Movements;
import prj.sch.chocosheep.TextFormat;

public class TextTimeSize extends Text {
    private int targetSize;
    private long duration;

    private long startTime;
    private float startSize;

    public TextTimeSize(int x, int y, String text, TextFormat textFormat, long duration, int targetSize) {
        super(x, y, text, textFormat);
        this.duration = duration;
        this.targetSize = targetSize;

        init();
    }

    private void init() {
        startTime = System.currentTimeMillis();
        startSize = super.textFormat.getSize();
    }

    @Override
    public void tick() {
        double progress = Movements.easeOut((double) (System.currentTimeMillis() - startTime) / duration);

        super.textFormat.setSize((float) ((targetSize - startSize) * progress + startSize));
    }

    public boolean isEnd() {
        return System.currentTimeMillis() >= startTime + duration;
    }

    public void setX(int x) {
        super.setX(x);
    }

    public void setY(int y) {
        super.setY(y);
    }
}
