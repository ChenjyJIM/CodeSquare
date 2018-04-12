package com.example.a54545.phpconnectnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class test extends AppCompatActivity {
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(" ");
        setContentView(R.layout.activity_test);
        btn1=(Button)findViewById(R.id.button6);
        Bundle bundle2=this.getIntent().getExtras();
        String value=bundle2.getString("key");
        btn1.setText(value);

    }
}
