package prj.sch.chocosheep.util;

public class Timer {
    private long duration;

    private long startTime, endTime;

    public Timer(long duration) {
        this.duration = duration;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        endTime = startTime + duration;
    }

    public boolean check() {
        return System.currentTimeMillis() >= endTime;
    }
}
