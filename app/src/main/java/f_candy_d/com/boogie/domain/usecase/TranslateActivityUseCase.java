package f_candy_d.com.boogie.domain.usecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by daichi on 17/09/01.
 */

public class TranslateActivityUseCase {

    public static void translate(Activity from, Class<?> to) {
        translate(from, to, null);
    }

    public static void translate(Activity from, Class<?> to, Bundle extras) {
        Intent intent = new Intent(from, to);
        if (extras != null) {
            intent.putExtras(extras);
        }
        from.startActivity(intent);
    }
}
