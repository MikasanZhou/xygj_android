package com.xygj.app.jinrirong.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;

/**
 * 拍照和相册选择对话框
 * Created by Administrator on 2018/3/10.
 */

public class ChooseDialog extends DialogFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_title_line)
    View titleLine;
    @BindView(R.id.tv_choice_one)
    TextView tvChoiceOne;
    @BindView(R.id.tv_choice_two)
    TextView tvChoiceTwo;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private static String title;
    private static String firstChoice, secondChoice;
    private static OnChoiceClickListener choiceClickListener;

    public static ChooseDialog newInstance(String ti, String first, String second,
                                           OnChoiceClickListener listener) {
        title = ti;
        firstChoice = first;
        secondChoice = second;
        choiceClickListener = listener;
        return new ChooseDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_choose, container, false);
        ButterKnife.bind(this, view);
        if (title == null) {
            tvTitle.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvChoiceOne.setText(firstChoice);
        tvChoiceTwo.setText(secondChoice);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick(R.id.tv_choice_one)
    public void onFirstClicked() {
        choiceClickListener.onChooseFirst();
        dismiss();
    }

    @OnClick(R.id.tv_choice_two)
    public void onSecondClicked() {
        choiceClickListener.onChooseSecond();
        dismiss();
    }

    @OnClick(R.id.tv_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    public interface OnChoiceClickListener {
        void onChooseFirst();

        void onChooseSecond();
    }
}
