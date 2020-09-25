package com.swufe.exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    double dollar= 0.1465;
    double euro= 0.1259;
    double won= 171.7179;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.edit);
    }
    public void exchange(View btn){
        Toast.makeText(this,"exchanging...",Toast.LENGTH_SHORT).show();
        Log.i(TAG,"onclicked");

        String a=et.getText().toString();
        if(btn.getId()==R.id.btn_1){
            double r1 = dollar * Double.parseDouble(a);
            et.setText(""+r1);
        }else if(btn.getId()==R.id.btn_2){
            double r2 = euro * Double.parseDouble(a);
            et.setText(""+r2);
        }else{
            double r3 = won * Double.parseDouble(a);
            et.setText(""+r3);
        }
    }
    public void open(View v){
        Log.i("main","onClick msg");
        Intent o = new Intent(MainActivity.this,MainActivity2.class);
        Bundle bdl = new Bundle();
        bdl.putDouble("dollar_rate_key",dollar);
        bdl.putDouble("euro_rate_key",euro);
        bdl.putDouble("won_rate_key",won);
        /*不打包，分别传入
        o.putExtra("dollar_rate_key",dollar);
        o.putExtra("dollar_rate_key",euro);
        o.putExtra("dollar_rate_key",won);
        Log.i(TAG,"openOne:dollarRate="+dollar);
        Log.i(TAG,"openOne:euroRate="+euro);
        Log.i(TAG,"openOne:wonRate="+won);*/
        o.putExtras(bdl);
        startActivityForResult(o,1);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(1 == requestCode && resultCode == 2){
            Bundle bundle = data.getExtras();
            dollar = bundle.getDouble("dollar_rate_key");
            euro = bundle.getDouble("euro_rate_key");
            won = bundle.getDouble("won_rate_key");
            Log.i(TAG,"onActivityResult:dollarRate="+dollar);
            Log.i(TAG,"onActivityResult:euroRate="+euro);
            Log.i(TAG,"onActivityResult:wonRate="+won);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}