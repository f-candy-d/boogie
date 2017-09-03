package f_candy_d.com.boogie.presentation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import f_candy_d.com.boogie.R;

/**
 * Created by daichi on 17/09/03.
 */

abstract public class CircularRevealOverlapDialog {

    private View mDialogView;
    private Dialog mDialog;

    public CircularRevealOverlapDialog(Context context, View dialogView) {
        mDialogView = dialogView;

        mDialog = new Dialog(context, R.style.NoDimDialogFragment);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mDialogView);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    protected View getDialogView() {
        return mDialogView;
    }

    public CircularRevealOverlapDialog setupDialogRevealAnim(final int cx, final int cy, final int revealDuration, final int dismissDuration) {
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealDialog(cx, cy, revealDuration);
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == android.view.KeyEvent.KEYCODE_BACK) {
                    dismissDialog(cx, cy, dismissDuration);
                    return true;
                }
                return false;
            }
        });

        return this;
    }

    private void revealDialog(final int cx, final int cy, int duration) {
        int endRadius = (int) Math.hypot(mDialogView.getWidth(), mDialogView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mDialogView, cx, cy, 0, endRadius);
        mDialogView.setVisibility(View.VISIBLE);
        anim.setDuration(duration);
        anim.start();
    }

    private void dismissDialog(final int cx, final int cy, int duration) {
        int endRadius = (int) Math.hypot(mDialogView.getWidth(), mDialogView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mDialogView, cx, cy, endRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDialog.dismiss();
                mDialogView.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    public Dialog getDialog() {
        return mDialog;
    }
}
