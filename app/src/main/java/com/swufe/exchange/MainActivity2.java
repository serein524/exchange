package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    EditText dollar,euro,won;
    Double newdollarrate,neweurorate,newwonrate;
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
        String dollarRate = ""+bundle.getDouble("dollar_rate_key");
        String euroRate = ""+bundle.getDouble("euro_rate_key");
        String wonRate = ""+bundle.getDouble("won_rate_key");
        dollar = findViewById(R.id.et1);
        euro = findViewById(R.id.et2);
        won = findViewById(R.id.et3);
        dollar.setText(dollarRate);
        euro.setText(euroRate);
        won.setText(wonRate);

    }
    public void modify(View v){
        newdollarrate=Double.parseDouble(dollar.getText().toString());
        neweurorate=Double.parseDouble(euro.getText().toString());
        newwonrate=Double.parseDouble(won.getText().toString());
        Intent it = getIntent();
        Bundle bdl = new Bundle();
        bdl.putDouble("dollar_rate_key",newdollarrate);
        bdl.putDouble("euro_rate_key",neweurorate);
        bdl.putDouble("won_rate_key",newwonrate);
        it.putExtras(bdl);
        setResult(2,it);//设置resultCode以及带回的数据
        finish();//返回到调用界面
    }
}