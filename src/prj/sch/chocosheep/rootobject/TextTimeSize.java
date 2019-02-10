package prj.sch.chocosheep.rootobject;

import prj.sch.chocosheep.Movements;
import prj.sch.chocosheep.TextFormat;

public class TextTimeSize extends Text {
    private int x, y, targetSize;
    private String text;
    private TextFormat textFormat;
    private long duration;

    private long startTime;

    public TextTimeSize(int x, int y, String text, TextFormat textFormat, long duration, int targetSize) {
        super(x, y, text, textFormat);
        this.x = x;
        this.y = y;
        this.text = text;
        this.textFormat = textFormat;
        this.duration = duration;
        this.targetSize = targetSize;

        init();
    }

    private void init() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        double progress = Movements.easeOut((double) (System.currentTimeMillis() - startTime) / duration);
    }
}
