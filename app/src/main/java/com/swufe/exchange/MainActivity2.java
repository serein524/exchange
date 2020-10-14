package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
public class MainActivity2 extends AppCompatActivity {
    EditText dollar, euro, won;
    Float newdollarrate, neweurorate, newwonrate;
    private Handler handler;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*单条取数据
        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2 = intent.getFloatExtra("euro_rate_key",0.0f);
        float won2 = intent.getFloatExtra("won_rate_key",0.0f); */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String dollarRate = "" + bundle.getFloat("dollar_rate_key");
        String euroRate = "" + bundle.getFloat("euro_rate_key");
        String wonRate = "" + bundle.getFloat("won_rate_key");
        dollar = findViewById(R.id.et1);
        euro = findViewById(R.id.et2);
        won = findViewById(R.id.et3);
        dollar.setText(dollarRate);
        euro.setText(euroRate);
        won.setText(wonRate);
//线程通信
        //主线程中的处理消息方法
//        handler = new Handler() {
//            public void handleMessage(Message msg){
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case 5:
//                        String str = (String) msg.obj;
//                        Log.i(TAG,"handleMessage: getMessage msg = " + str);
//
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + msg.what);
//                }
//            }
//        };

        //新建线程
        new Thread() {
            public void run() {
//                super.run();
//                Message message = new Message();
//                message.what=5;
//                message.obj="hello from newthread";
//                handler.sendMessage(message);
                //获取网络数据(主线程不能获取网络数据
//                URL url = null;
//                try {
//                    url = new URL("http://www.usd-cny.com/bankofchina.htm");
//                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
//                    InputStream in = http.getInputStream();
//                    String html = inputStream2String(in);
//                    Log.i(TAG, "run: html=" + html);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            private String inputStream2String(InputStream inputStream)
//                    throws IOException {
//                final int bufferSize = 1024;
//                final char[] buffer = new char[bufferSize];
//                final StringBuilder out = new StringBuilder();
//                Reader in = new InputStreamReader(inputStream, "gb2312");
//                while (true) {
//                    int rsz = in.read(buffer, 0, buffer.length);
//                    if (rsz < 0)
//                        break;
//                    out.append(buffer, 0, rsz);
//                }
//                return out.toString();
            }
        }.start();

    }

    public void modify(View v) {
        newdollarrate = Float.parseFloat(dollar.getText().toString());
        neweurorate = Float.parseFloat(euro.getText().toString());
        newwonrate = Float.parseFloat(won.getText().toString());
        Intent it = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("dollar_rate_key", newdollarrate);
        bdl.putFloat("euro_rate_key", neweurorate);
        bdl.putFloat("won_rate_key", newwonrate);
        it.putExtras(bdl);
        setResult(2, it);//设置resultCode以及带回的数据
        finish();//返回到调用界面
    }

    public void check(View v) {
        Log.i("main", "onClick check:jump to checkrate");
        Intent inte = new Intent(MainActivity2.this, RateListActivity.class);
        startActivity(inte);
    }
    public void showlist(View v) {
        Log.i("main", "onClick check:jump to item_view");
        Intent inte = new Intent(MainActivity2.this, list_view.class);
        startActivity(inte);
    }
    public void selfdefined(View v) {
        Log.i("main", "onClick check:jump to list_view");
        Intent inte = new Intent(MainActivity2.this, list_view1.class);
        startActivity(inte);
    }
}
