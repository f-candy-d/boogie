package f_candy_d.com.boogie.domain.structure;

import f_candy_d.com.boogie.utils.Quantizable;

/**
 * Created by daichi on 17/08/30.
 */

public enum TaskType implements Quantizable {
    FUZZY_TERM {@Override public int quantize() {return 0;}},
    EXACT_TERM {@Override public int quantize() {return 1;}}
}
