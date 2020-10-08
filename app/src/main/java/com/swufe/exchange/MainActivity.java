package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    float dollar, euro, won;
    /*float dollar= (float) 0.1465;
    float euro= (float)0.1259;
    float won= (float)171.7179;*/
    EditText et;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.edit);

        //生成myrate.xml文件
        //获取SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate", (float) 0.1465);
        editor.putFloat("euro_rate", (float) 0.1259);
        editor.putFloat("won_rate", (float) 171.7179);
        //editor.commit();
        editor.apply();//commit是同步过程，apply是异步，后台将数据保存在硬件
        //线程通信

    }

    public void exchange(View btn) {
        Toast.makeText(this, "exchanging...", LENGTH_SHORT).show();
        Log.i(TAG, "onclicked");
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollar = sharedPreferences.getFloat("dollar_rate", 0.01f);
        euro = sharedPreferences.getFloat("euro_rate", 0.01f);
        won = sharedPreferences.getFloat("won_rate", 0.01f);
        String a = et.getText().toString();
        if (btn.getId() == R.id.btn_1) {
            float r1 = dollar * Float.parseFloat(a);
            et.setText("" + r1);
        } else if (btn.getId() == R.id.btn_2) {
            float r2 = euro * Float.parseFloat(a);
            et.setText("" + r2);
        } else {
            float r3 = won * Float.parseFloat(a);
            et.setText("" + r3);
        }

    }

    public void open(View v) {
        Log.i("main", "onClick msg");
        Intent o = new Intent(MainActivity.this, MainActivity2.class);
        Bundle bdl = new Bundle();
        bdl.putFloat("dollar_rate_key", dollar);
        bdl.putFloat("euro_rate_key", euro);
        bdl.putFloat("won_rate_key", won);
        /*不打包，分别传入
        o.putExtra("dollar_rate_key",dollar);
        o.putExtra("dollar_rate_key",euro);
        o.putExtra("dollar_rate_key",won);
        Log.i(TAG,"openOne:dollarRate="+dollar);
        Log.i(TAG,"openOne:euroRate="+euro);
        Log.i(TAG,"openOne:wonRate="+won);*/
        o.putExtras(bdl);
        startActivityForResult(o, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == requestCode && resultCode == 2) {
            Bundle bundle = data.getExtras();
            dollar = bundle.getFloat("dollar_rate_key");
            euro = bundle.getFloat("euro_rate_key");
            won = bundle.getFloat("won_rate_key");
            Log.i(TAG, "onActivityResult:dollarRate=" + dollar);
            Log.i(TAG, "onActivityResult:euroRate=" + euro);
            Log.i(TAG, "onActivityResult:wonRate=" + won);
            //修改保存内容
            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate", dollar);
            editor.putFloat("euro_rate", euro);
            editor.putFloat("won_rate", won);
            editor.apply();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void run(View view){
        //线程通信
        final Handler handler;
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 5) {
                    String s = (String) msg.obj;
                    Log.i(TAG, "" + s);
                }
            }
        };
        thread = new Thread() {
            public void run() {
                Message message = new Message();
                message.what = 5;
                message.obj = "hello from run";
                handler.sendMessage(message);
//                URL url = null;
//                try {
//                    url = new URL("www.usd-cny.com/bankofchina.htm");
//                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
//                    InputStream in = http.getInputStream();
//                    String html = inputStream2String(in);
//                    Log.i(TAG, "run: html=" + html);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            private String inputStream2String(InputStream inputStream) throws IOException {
                final int bufferSize = 1024;
                final char[] buffer = new char[bufferSize];
                final StringBuilder out = new StringBuilder();
                Reader in = new InputStreamReader(inputStream, "gb2312");
                while (true) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
                return out.toString();
            }

        };


    }
}