package com.example.a54545.phpconnectnew.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.boot.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText name,password,repassword,sex,age,phone,address;
    private TextView login;
    private Boolean isSuccess=false;
    private Intent intent_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initView();
        this.initListener();
    }

    private void initView() {
        register=(Button)findViewById(R.id.register_button);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.repassword);
        sex=(EditText)findViewById(R.id.sex);
        age=(EditText)findViewById(R.id.age);
        phone=(EditText)findViewById(R.id.phone);
        address=(EditText)findViewById(R.id.address);
        login=(TextView)findViewById(R.id.login);
        intent_login=new Intent(RegisterActivity.this,MainActivity.class);
    }

    private void initListener() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"用户名不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (password.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"密码不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (repassword.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"确认密码不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (!password.getText().toString().equals(repassword.getText().toString()))
                    Toast.makeText(RegisterActivity.this,"两次密码输入不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (sex.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"性别不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (age.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"年龄不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (phone.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"联系方式不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else if (address.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this,"地址不能为空，请重新输入！",Toast.LENGTH_SHORT).show();
                else {
                    new Thread(new insert_user()).start();
                    try {
                        Thread.currentThread().sleep(200);//阻断0.1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(isSuccess)
                    {
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "注册失败！" +"原因不明.....", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_login);
            }
        });
    }


    private class insert_user implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/insertuser.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<>(); //准备传输的数据
            params.add(new BasicNameValuePair("name", name.getText().toString()));
            params.add(new BasicNameValuePair("password",password.getText().toString()));
            params.add(new BasicNameValuePair("sex",sex.getText().toString()));
            params.add(new BasicNameValuePair("phone",phone.getText().toString()));
            params.add(new BasicNameValuePair("address",address.getText().toString()));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    JSONObject jsonObject=new JSONObject(builder.toString());
                    isSuccess=jsonObject.getBoolean("isSuccess");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
