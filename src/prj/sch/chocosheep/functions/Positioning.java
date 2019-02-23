package prj.sch.chocosheep.functions;

public class Positioning {
    public static int center(int a, int b) {
        return (a - b) / 2;
    }

    public static float center(float a, float b) {
        return (a - b) / 2;
    }

    public static int range(int value, int min, int max) {
        if (value <= min) {
            return min;
        } else if (value >= max) {
            return max;
        } else {
            return value;
        }
    }
}
