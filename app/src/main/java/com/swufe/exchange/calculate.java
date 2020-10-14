package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class calculate extends AppCompatActivity {
    private static final String TAG = "calculate";
    EditText edit;
    TextView title,result;
    String rate,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        title = findViewById(R.id.country);
        Intent intent = getIntent();
        country = intent.getStringExtra("Title");
        rate = intent.getStringExtra("Detail");
        title.setText(country);
        edit = findViewById(R.id.input);
        result = findViewById(R.id.result);
        //设置EditText实时监听，实时获取编辑框内容
        TextWatcher watcher = new TextWatcher() {
            //textwatcher是一个接口类，以下实现它的3个抽象方法
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s = " + s + ", start = " + start +
                        ", count = " + count + ", after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s = " + s + ", start = " + start +
                        ", before = " + before + ", count = " + count);
                String num = edit.getText().toString();
                Pattern p = Pattern.compile("[0-9]*");//判断输入金额是否规范
                Matcher m = p.matcher(s);
                if(m.matches()){
                    float x = Float.parseFloat(num);
                    float y = Float.parseFloat(rate);
                    float re = x*y;
                    result.setText(re+"");
                }else{
                    Toast.makeText(calculate.this,"请输入正确的转换金额",Toast.LENGTH_SHORT).show();
                    result.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);

            }
        };
        edit.addTextChangedListener(watcher);


    }
}