package com.app.android.app.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.android.app.R;



public class DialogConfirm extends Dialog {

    private OnClickListener mListener;


    public DialogConfirm(@NonNull Context context) {
        this(context, R.style.dialog_transparent_style);

    }


    public DialogConfirm(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected DialogConfirm(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        Window window = getWindow();
        setContentView(view);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setWindowAnimations(R.style.dialog_bottom_animation_style);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);


        view.findViewById(R.id.browse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onBrowse();
                }
            }
        });
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onSave();
                }
            }
        });


        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {

        void onSave();

        void onBrowse();
    }
}
