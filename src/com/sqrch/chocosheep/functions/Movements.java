package com.sqrch.chocosheep.functions;

public class Movements {
    public static double easeOut(double value) {
        return -Math.pow(value - 1, 2) + 1;
    }

    public static double easeIn(double value) {
        return Math.pow(value, 2);
    }
}
