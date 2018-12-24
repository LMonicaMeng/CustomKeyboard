package com.example.customkeyboard.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.customkeyboard.util.DensityUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * 九宫格键盘适配器
 */
public class KeyBoardAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Map<String, String>> valueList;
    private Map<String, Object> attrsMap;
    private int clickTemp = -1;//标识被选择的item
    private int[] clickedList;//这个数组用来存放item的点击状态

    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList, Map<String, Object> attrsMap) {
        this.mContext = mContext;
        this.valueList = valueList;
        this.attrsMap = attrsMap;
        clickedList = new int[valueList.size()];
        for (int i = 0; i < valueList.size(); i++) {
            clickedList[i] = 0;      //初始化item点击状态的数组
        }
    }

    public void setSeclection(int posiTion) {
        clickTemp = posiTion;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_virtual_keyboard, null);
            viewHolder = new ViewHolder();
            viewHolder.btnKey = (TextView) convertView.findViewById(R.id.btn_keys);
            viewHolder.imgDelete = (RelativeLayout) convertView.findViewById(R.id.imgDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 10) {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
        } else if (position == 11) {
            viewHolder.btnKey.setBackgroundResource(R.drawable.keyboard_delete);
            int cancleBackground = (int) attrsMap.get("cancleBackground");
            viewHolder.btnKey.setBackgroundColor(cancleBackground);
            viewHolder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgDelete.setVisibility(View.INVISIBLE);
            viewHolder.btnKey.setVisibility(View.VISIBLE);
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
        }

        float keyboardHeight = (float) attrsMap.get("keyboardHeight");
        ViewGroup.LayoutParams layoutParams = viewHolder.btnKey.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(mContext,keyboardHeight/4);
        viewHolder.btnKey.setLayoutParams(layoutParams);
        float keyboardTextSize = (float) attrsMap.get("keyboardTextSize");
        viewHolder.btnKey.setTextSize(keyboardTextSize);
        int keyboardTextColor = (int) attrsMap.get("keyboardTextColor");
        viewHolder.btnKey.setTextColor(keyboardTextColor);
        return convertView;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView btnKey;
        public RelativeLayout imgDelete;
    }
}
