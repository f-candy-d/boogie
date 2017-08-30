package f_candy_d.com.boogie.utils;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public enum Month implements Quantizable {
    JANUARY {@Override public int quantize() {return Calendar.JANUARY;}},
    FEBRUARY {@Override public int quantize() {return Calendar.FEBRUARY;}},
    MARCH {@Override public int quantize() {return Calendar.MARCH;}},
    APRIL {@Override public int quantize() {return Calendar.APRIL;}},
    MAY {@Override public int quantize() {return Calendar.MAY;}},
    JUNE {@Override public int quantize() {return Calendar.JUNE;}},
    JULY {@Override public int quantize() {return Calendar.JULY;}},
    AUGUST {@Override public int quantize() {return Calendar.AUGUST;}},
    SEPTEMBER {@Override public int quantize() {return Calendar.SEPTEMBER;}},
    OCTOBER {@Override public int quantize() {return Calendar.OCTOBER;}},
    NOVEMBER {@Override public int quantize() {return Calendar.NOVEMBER;}},
    DECEMBER {@Override public int quantize() {return Calendar.DECEMBER;}};

    public static Month from(int month) {
        Month[] monthes = values();
        for (Month m : monthes) {
            if (m.quantize() == month) {
                return m;
            }
        }
        return null;
    }

    public int getValue() {
        return quantize();
    }
}
