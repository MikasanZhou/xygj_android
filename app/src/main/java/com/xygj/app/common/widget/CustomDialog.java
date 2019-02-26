package com.xygj.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xygj.app.R;

/**
 * 自定义dialog
 * Created by xuyougen on 2018/3/5.
 * <p>
 * 使用方法
 * new CustomDialog.Builder({@link Context})
 * .setTitle("标题")
 * .setContent("提示内容")
 * .setListener({@link OnButtonClickListener))
 * .build()
 * .show();
 */

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置dialog 窗体的宽度
        //getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mContent;

        private OnButtonClickListener mListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setListener(OnButtonClickListener listener) {
            mListener = listener;
            return this;
        }

        public CustomDialog build() {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.CustomDialog);

            View layout = View.inflate(mContext, R.layout.layout_custom_dialog, null);
            TextView title = layout.findViewById(R.id.tv_title_custom_dialog);
            TextView content = layout.findViewById(R.id.tv_content_custom_dialog);

            //设置标题和内容
            if (mTitle != null) title.setText(mTitle);
            if (mContent != null) content.setText(mContent);

            //设置监听
            if (mListener != null) {
                TextView confirm = layout.findViewById(R.id.tv_confirm_custom_dialog);
                TextView cancel = layout.findViewById(R.id.tv_cancel_custom_dialog);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onConfirm(dialog);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCancel(dialog);
                    }
                });
            }
            //将布局添加到 dialog 中
            dialog.setContentView(layout);
            return dialog;
        }
    }

    /**
     * 监听接口
     */
    public interface OnButtonClickListener {
        void onConfirm(Dialog dialog);

        void onCancel(Dialog dialog);
    }
}
