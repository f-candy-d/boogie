package f_candy_d.com.boogie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.utils.CircularRevealOverlapDialog;

/**
 * Created by daichi on 17/09/03.
 */

public class WhatAddDialog extends CircularRevealOverlapDialog {

    private OnSelectionChosenListener mListener;

    public WhatAddDialog(Context context) {
        super(context, View.inflate(context, R.layout.what_add_dialog, null));
        initContents();
    }

    public WhatAddDialog setOnSelectionChosenListener(OnSelectionChosenListener listener) {
        mListener = listener;
        return this;
    }

    private void initContents() {
        Button button = getDialogView().findViewById(R.id.add_event_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(Selection.ADD_EVENT);
            }
        });
    }

    private void onButtonClick(Selection selection) {
        if (mListener != null) {
            mListener.onSelectionChosen(selection, getDialog());
        }
    }

    public enum Selection {
        ADD_EVENT,
        ADD_PLAN
    }

    public interface OnSelectionChosenListener {
        void onSelectionChosen(Selection selection, Dialog dialog);
    }
}
