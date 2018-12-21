package com.example.customkeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.administrator.myapplication.R;
import com.example.customkeyboard.widget.CustomKeyboardView;

import java.lang.reflect.Method;

public class CustomeKeyBoardActivity extends AppCompatActivity {
    private CustomKeyboardView customKeyboardView;
    private EditText textAmount;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_key_board);
        initView();
    }

    private void initView() {
        customKeyboardView = (CustomKeyboardView) findViewById(R.id.customKeyboardView);
        textAmount = (EditText) findViewById(R.id.textAmount);
        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            textAmount.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(textAmount, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        textAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customKeyboardView.setFocusable(true);
                customKeyboardView.setFocusableInTouchMode(true);
                customKeyboardView.setVisibility(View.VISIBLE);
            }
        });
        //点击删除按键
        customKeyboardView.setOnClickCancleListener(new CustomKeyboardView.OnClickCancleListener() {
            @Override
            public void onCancleClick() {
                amount = textAmount.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    textAmount.setText(amount);
                    Editable ea = textAmount.getText();
                    textAmount.setSelection(ea.length());
                }
            }
        });
        //点击输入数字的按键
        customKeyboardView.setOnClickNumListener(new CustomKeyboardView.OnClickNumListener() {
            @Override
            public void onNumClick(String value) {
                amount = textAmount.getText().toString().trim();
                amount = amount+value;
                textAmount.setText(amount);
                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            }
        });

        //点击输入点
        customKeyboardView.setmOnClickPointListener(new CustomKeyboardView.OnClickPointListener() {
            @Override
            public void onPointClick(String value) {
                amount = textAmount.getText().toString().trim();
                if (!amount.contains(".")) {
                    amount = amount + value;
                    textAmount.setText(amount);
                    Editable ea = textAmount.getText();
                    textAmount.setSelection(ea.length());
                }
            }
        });
    }
}
