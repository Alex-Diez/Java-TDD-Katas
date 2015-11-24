package org.mesh;

public enum Int {
    ZERO, ONE, TWO, THREE, FOUR, FIFE, SIX, SEVEN, EIGHT, NINE;

    public static Int fromInteger(int integer) {
        switch (integer) {
            case 0: return ZERO;
            case 1: return ONE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIFE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            default: throw new RuntimeException("Impossible");
        }
    }
}
