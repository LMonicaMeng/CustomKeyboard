package com.example.customkeyboard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟键盘
 */
public class CustomKeyboardView extends RelativeLayout {
    private Context context;
    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    private float keyboardSpacing;
    private int keyboardLineColor;
    private int keyboardTextColor;
    private float keyboardTextSize;
    private int cancleBackground;
    private int pressBackground;
    private float keyboardHeight;
    private Map<String, Object> attrsMap;

    public CustomKeyboardView(Context context) {
        this(context, null);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);
        init(attrs);
        valueList = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.gv_keybord);
        gridView.setVerticalSpacing((int)keyboardSpacing);
        gridView.setHorizontalSpacing((int) keyboardSpacing);
        gridView.setBackgroundColor(keyboardLineColor);
        initValueList();
        setupView();
        addView(view);
    }

    private void init(AttributeSet attrs) {
        attrsMap = new HashMap<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.keyboard);
        keyboardTextSize = typedArray.getDimension(R.styleable.keyboard_keyboardTextSize, 1);
        keyboardTextColor = typedArray.getColor(R.styleable.keyboard_keyboardTextColor, Color.BLACK);
        keyboardSpacing = typedArray.getDimension(R.styleable.keyboard_keyboardSpacing, 1);
        keyboardLineColor = typedArray.getColor(R.styleable.keyboard_keyboardLineColor, context.getResources().getColor(R.color.keyboad_bg));
        cancleBackground = typedArray.getColor(R.styleable.keyboard_cancleBackground, getResources().getColor(R.color.cancle_key));
        pressBackground = typedArray.getColor(R.styleable.keyboard_pressBackground, getResources().getColor(R.color.keyboard_press_bg));
        keyboardHeight = typedArray.getDimension(R.styleable.keyboard_keyboardHeight, 300);
        attrsMap.put("keyboardTextSize",keyboardTextSize);
        attrsMap.put("keyboardTextColor",keyboardTextColor);
        attrsMap.put("cancleBackground",cancleBackground);
        attrsMap.put("pressBackground",pressBackground);
        attrsMap.put("keyboardHeight",keyboardHeight);
        typedArray.recycle();
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", String.valueOf(0));
            } else if (i == 11) {
                map.put("name", ".");
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    private void setupView() {
        final com.example.customkeyboard.widget.KeyBoardAdapter keyBoardAdapter = new com.example.customkeyboard.widget.KeyBoardAdapter(context, valueList,attrsMap);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyBoardAdapter.setSeclection(position);
                keyBoardAdapter.notifyDataSetChanged();
                if (position < 11 && position != 10) {//点击0-9按钮
                    if (position == 9) {
                        mOnClickNumListener.onNumClick(String.valueOf(0));
                    } else {
                        mOnClickNumListener.onNumClick(String.valueOf(position + 1));
                    }
                } else {
                    if (position == 10) {//点击“.”
                        mOnClickPointListener.onPointClick(".");
                    }

                    if (position == 11) {//点击退格
                        mOnClickCancleListener.onCancleClick();
                    }
                }
            }
        });
    }

    OnClickNumListener mOnClickNumListener;

    public void setOnClickNumListener(OnClickNumListener listener) {
        mOnClickNumListener = listener;
    }

    //点击数字按键
    public interface OnClickNumListener {
        void onNumClick(String value);
    }

    private OnClickCancleListener mOnClickCancleListener;

    public void setOnClickCancleListener(OnClickCancleListener listener) {
        mOnClickCancleListener = listener;
    }

    //点击删除键
    public interface OnClickCancleListener {
        void onCancleClick();
    }

    private OnClickPointListener mOnClickPointListener;

    public void setmOnClickPointListener(OnClickPointListener listener) {
        mOnClickPointListener = listener;
    }

    //点击输入点的按键
    public interface OnClickPointListener {
        void onPointClick(String value);
    }
}
