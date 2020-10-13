package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class list_view extends AppCompatActivity {
    private Handler handler;
    ListView listView;

    private static final String TAG = "MainActivity3";
    List<HashMap<String, String>> listItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        listView=this.findViewById(R.id.listview);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 6) {
                    List<HashMap<String,String>> listItem = (List<HashMap<String, String>>) msg.obj;
                    SimpleAdapter listItemAdapter = new SimpleAdapter(list_view.this,
                            listItem,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                    listView.setAdapter(listItemAdapter);

                }
            }
        };


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
                message.what = 6;
                message.obj=listItems;
                handler.sendMessage(message);
                Log.i("MainActivity3", listItems.toString());
            }
        }.start();
    }
}