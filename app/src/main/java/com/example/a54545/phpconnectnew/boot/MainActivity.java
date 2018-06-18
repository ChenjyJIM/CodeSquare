package com.example.a54545.phpconnectnew.boot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.admin.AdminActivity;
import com.example.a54545.phpconnectnew.admin.DataActivity1;
import com.example.a54545.phpconnectnew.customer.RegisterActivity;
import com.example.a54545.phpconnectnew.customer.UserActivity;

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

/**
 * APP开启界面，分为用户入口与商家入口，由不同远程服务器中的php文件来对输入参数进行校验。
 */
public class MainActivity extends AppCompatActivity {
    int id=100;
    String account,loginword;
    String type;
    String test;
    boolean isSuccess=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(" ");
        final  EditText e1=(EditText)findViewById(R.id.editText);
        final EditText e2=(EditText)findViewById(R.id.editText2);
        Spinner sp = (Spinner) findViewById(R.id.sp);
        Button b1 = (Button) findViewById(R.id.button);
        List<String> list=new ArrayList<>();
        list.add("商家入口");
        list.add("用户入口");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);//为下拉框添加适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();//获取Spinner控件的适配器
                type=adapter.getItem(position);
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent){}
        });
        TextView register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent_register);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                account=e1.getText().toString();
                loginword=e2.getText().toString();
            if(type.equals("商家入口"))
            {
                new Thread(new RequestThread_admin()).start();
                try {
                    Thread.currentThread().sleep(100);//阻断0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isSuccess)
                {
                    Intent intent=new Intent(MainActivity.this,DataActivity1.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("account",account);
                    bundle.putString("test",test);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if(!isSuccess)
                {Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入" +".....", Toast.LENGTH_LONG).show();}
            }
            else if(type.equals("用户入口"))
            {
                new Thread(new RequestThread_user()).start();
                try {
                    Thread.currentThread().sleep(100);//阻断0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isSuccess)
                {
                    Intent intent=new Intent(MainActivity.this,UserActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("account",account);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if(!isSuccess)
                {Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入" +".....", Toast.LENGTH_LONG).show();}
            }
            else Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入" +".....", Toast.LENGTH_LONG).show(); //弹出式的提示框
            }
        });
    }
    private class RequestThread_admin implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/checkadmin.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", account));
            params.add(new BasicNameValuePair("password", loginword));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));
                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    JSONObject jsonObject   = new JSONObject(builder.toString());
                    isSuccess=jsonObject.getBoolean("isSuccess");
                    test=jsonObject.getString("test");
                    //在线程中判断是否得到成功从服务器得到数据
                }
                else {id=0;}
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class RequestThread_user implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/checkuser.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", account));
            params.add(new BasicNameValuePair("password", loginword));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                  JSONObject jsonObject=new JSONObject(builder.toString());
                    isSuccess=jsonObject.getBoolean("isSuccess");
                    //在线程中判断是否得到成功从服务器得到数据
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

