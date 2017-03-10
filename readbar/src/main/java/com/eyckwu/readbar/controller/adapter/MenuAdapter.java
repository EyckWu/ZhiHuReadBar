package com.eyckwu.readbar.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.model.bean.MenuItem;

import java.util.List;

/**
 * Created by Eyck on 2017/2/26.
 */

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<MenuItem> menuItems;

    public MenuAdapter(Context mContext,List<MenuItem> menuItems) {
        this.mContext = mContext;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.menu_item,null);
            viewHolder.menu_item = (TextView) convertView.findViewById(R.id.menu_item);
            viewHolder.isOrder = (ImageButton) convertView.findViewById(R.id.isOrder);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MenuItem menuItem = menuItems.get(position);
        viewHolder.menu_item.setText(menuItem.getName());
        if(menuItem.isOrder()) {
            viewHolder.isOrder.setBackgroundResource(R.drawable.home_arrow);
        }else {
            viewHolder.isOrder.setBackgroundResource(R.drawable.menu_follow);
        }
        return convertView;
    }
    static class ViewHolder{
        TextView menu_item;
        ImageButton isOrder;
    }
}
