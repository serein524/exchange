package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class list_view1 extends AppCompatActivity {
    private static final String TAG = "list_view1";
    private Handler handler;
    List listItems = null;
    ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view1);
        listView1=this.findViewById(R.id.listview1);
        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==8){
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    MyAdapter myAdapter = new MyAdapter(list_view1.this,
                            R.layout.activity_list_view1,
                            (ArrayList<HashMap<String, String>>) listItems);
                    //setListAdapter(myAdapter);
                    listView1.setAdapter(myAdapter);
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object itemAtPosition = listView1.getItemAtPosition(position);
                            //方法一：使用position定位到Hashmap，从中取得数据
                            HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
                            String titleStr = map.get("ItemTitle");
                            String detailStr = map.get("ItemDetail");
                            Log.i(TAG, "onItemClick: titleStr=" + titleStr);
                            Log.i(TAG, "onItemClick: detailStr=" + detailStr);
                            //方法二：使用findviewbyid获得点击的控件，从界面中获取它的值
//                            TextView title = (TextView) view.findViewById(R.id.itemTitle);
//                            TextView detail = (TextView) view.findViewById(R.id.itemDetail);
//                            String title2 = String.valueOf(title.getText());
//                            String detail2 = String.valueOf(detail.getText());
//                            Log.i(TAG, "onItemClick: title2=" + title2);
//                            Log.i(TAG, "onItemClick: detail2=" + detail2);
                            Intent o = new Intent(list_view1.this, calculate.class);
                            Bundle bdl = new Bundle();
                            bdl.putString("Title",titleStr);
                            bdl.putString("Detail",detailStr);
                            o.putExtras(bdl);
                            startActivity(o);

                        }
                    }) ;
                    }
            }

        };
//        listItems = new ArrayList<HashMap<String, String>>();
//        for (int i = 0; i < 10; i++) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemTitle", "Rate： " + i); // 标题文字
//            map.put("ItemDetail", "detail" + i); // 详情描述
//            listItems.add(map);
//        }


        new Thread() {
            public void run() {
                super.run();

                //获取网络数据(注意：主线程不能获取网络数据）
                String url = "http://www.usd-cny.com/bankofchina.htm";
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements tables = doc.getElementsByTag("table");
                    Element table = tables.get(0);//网页中第一个表格
                    Elements tds = table.getElementsByTag("td");
                    listItems = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < tds.size(); i += 6) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        String country = tds.get(i).text();//第一列为国家
                        String price = tds.get(i + 5).text();//第五列为折算率
                        float ra = 100f / Float.parseFloat(price);
                        String rate = ra + "";
                        map.put("ItemTitle", country);
                        map.put("ItemDetail", rate);
                        listItems.add(map);
                        Log.i(TAG, "run:add to listItems" + i);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 8;
                message.obj=listItems;
                handler.sendMessage(message);
                Log.i("list_view1", listItems.toString());
            }
        }.start();

    }
}