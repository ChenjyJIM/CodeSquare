package com.example.a54545.phpconnectnew;

import android.app.Activity;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int id=100;
    String username,password;
    String account,loginword;
    String type;
    String test;
    boolean isSuccess=false;
    //private String strResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(" ");
        //new Thread(new RequestThread()).start();
        //点击按钮更新TextView的内容
         final TextView tv = (TextView) findViewById(R.id.tv);
        final  EditText e1=(EditText)findViewById(R.id.editText);
        final EditText e2=(EditText)findViewById(R.id.editText2);
       // final TextView show=(TextView)findViewById(R.id.textView3);
        Spinner sp = (Spinner) findViewById(R.id.sp);
        Button b1 = (Button) findViewById(R.id.button);
        List<String> list=new ArrayList<String >();
        list.add("商家入口");
        list.add("用户入口");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);//为下拉框添加适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();//获取Spinner控件的适配器
                type=adapter.getItem(position);
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                account=e1.getText().toString();
                loginword=e2.getText().toString();
//                tv.setText(String.valueOf(id)+username+"\n"+password);
            if(type.equals("商家入口"))
            {
                new Thread(new RequestThread_admin()).start();
                try {
                    Thread.currentThread().sleep(100);//阻断0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //show.setText("成功与否:"+isSuccess);
                if(isSuccess)
                {
                    Intent intent=new Intent(MainActivity.this,AdminActivity.class);
                    Bundle bundle=new Bundle();
                  //  String str=txt.getText().toString();
                    bundle.putString("account",account);
                    bundle.putString("test",test);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if(!isSuccess)
                {
                    Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入" +".....", Toast.LENGTH_LONG).show();
                } //弹出式的提示框

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
                    //  String str=txt.getText().toString();
                    bundle.putString("account",account);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if(!isSuccess)
                {
                    Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入" +".....", Toast.LENGTH_LONG).show();
                } //弹出式的提示框
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
                    //得到Json对象
                    JSONObject jsonObject   = new JSONObject(builder.toString());
                    //通过得到键值对的方式得到值
//                    id = jsonObject.getInt("user_id");
//                    username = jsonObject.getString("user_name");
//                    password = jsonObject.getString("password");
                    isSuccess=jsonObject.getBoolean("isSuccess");
                    test=jsonObject.getString("test");
                    //在线程中判断是否得到成功从服务器得到数据
                }
                else
                {
                    id=0;
                }
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
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));
                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    //得到Json对象

//                    JSONArray jsonArray=new JSONArray(builder.toString());
//                  for(int i=0;i<jsonArray.length();i++)
//                  {
//                      JSONObject jsonObject=jsonArray.getJSONObject(i);
//                      isSuccess=jsonObject.getBoolean("isSuccess");
//                  }
                  JSONObject jsonObject=new JSONObject(builder.toString());
                    isSuccess=jsonObject.getBoolean("isSuccess");
                    //通过得到键值对的方式得到值
//                    id = jsonObject.getInt("user_id");
//                    username = jsonObject.getString("user_name");
//                    password = jsonObject.getString("password");

                    //在线程中判断是否得到成功从服务器得到数据
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

