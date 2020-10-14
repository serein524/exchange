package com.swufe.exchange;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";
    private Context context;
    private int recource;
    private ArrayList<HashMap<String,String>> list;
    public MyAdapter(Context context,
                     int resource,
                     ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public HashMap<String, String> getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }


//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
    //通过getview实例化布局并为控件赋值
    //convertView为用来加载数据的view
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,////LayoutInflater是用来找layout下xml布局文件，并且实例化
                    parent,
                    false);
        }
        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));
        return itemView;
    }
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view,
//                            int position, long id) {
//        Object itemAtPosition = listview.getItemAtPosition(position);
//        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
//        String titleStr = map.get("ItemTitle");
//        String detailStr = map.get("ItemDetail");
//        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
//        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
//        TextView title = (TextView) view.findViewById(R.id.itemTitle);
//        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
//        String title2 = String.valueOf(title.getText());
//        String detail2 = String.valueOf(detail.getText());
//        Log.i(TAG, "onItemClick: title2=" + title2);
//        Log.i(TAG, "onItemClick: detail2=" + detail2);
//    }
}
