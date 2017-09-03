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

public class WhatAddDialogWrapper {

    private View mContentView;
    private Dialog mDialog;

    public WhatAddDialogWrapper(Context context) {
        mContentView = View.inflate(context, R.layout.fragment_what_add_dialog, null);

        mDialog = new Dialog(context, R.style.NoDimDialogFragment);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mContentView);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public WhatAddDialogWrapper setupDialogRevealAnim(final int cx, final int cy, final int revealDuration, final int dismissDuration) {
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
        int endRadius = (int) Math.hypot(mContentView.getWidth(), mContentView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mContentView, cx, cy, 0, endRadius);
        mContentView.setVisibility(View.VISIBLE);
        anim.setDuration(duration);
        anim.start();
    }

    private void dismissDialog(final int cx, final int cy, int duration) {
        int endRadius = (int) Math.hypot(mContentView.getWidth(), mContentView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(mContentView, cx, cy, endRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDialog.dismiss();
                mContentView.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    public Dialog getDialog() {
        return mDialog;
    }
}
