package f_candy_d.com.boogie.utils;

/**
 * Created by daichi on 17/08/30.
 */

public enum Month implements Quantizable {

    APRIL {@Override public int quantize() {return 0;}};

    public int value() {
        return quantize();
    }
}
