package com.example.a54545.phpconnectnew.admin;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.entity.Product;

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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

public class ShowProduct extends AppCompatActivity {
    private Product product=new Product();
    private TextView tv1;
    private Button purchase,add,decline;
    private EditText number;
    private ImageView image;
    private int count;
    private double totalprice,price;
    private String account,time;
    private Boolean isSuccess=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        image=(ImageView)findViewById(R.id.imageView3);
        tv1=(TextView)findViewById(R.id.textView27) ;
        purchase=(Button)findViewById(R.id.button7);
        add=(Button)findViewById(R.id.button8);
        decline=(Button)findViewById(R.id.button9);
        number=(EditText)findViewById(R.id.editText3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number.setText(String.valueOf(Integer.parseInt(number.getText().toString())+1));
                count=Integer.parseInt(number.getText().toString());
                totalprice=price*count;
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(number.getText().toString())>0) {
                    number.setText(String.valueOf(Integer.parseInt(number.getText().toString()) - 1));
                    count=Integer.parseInt(number.getText().toString());
                    totalprice=price*count;
                }
                else
                    Toast.makeText(ShowProduct.this, "件数至少为1", Toast.LENGTH_LONG).show();

            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
         time = formatter.format(curDate);
        Bundle bundle=this.getIntent().getExtras();
        account=bundle.getString("account");
        product =(Product) bundle.getSerializable("product");
        price= product.getPrice();
        if(product.getName().trim().equals("焦糖玛奇朵"))
        {
            image.setImageResource(R.drawable.jiaotang);
        }
        if(product.getName().trim().equals("莫卡可可碎片星冰乐"))
        {
            image.setImageResource(R.drawable.moka);
        }
        tv1.setText("产品名："+product.getName()+"\n"
        +"产品价格： "+product.getPrice()+"\n"
        +"产品描述： "+product.getDescription()+"\n");
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new RequestThread_insert()).start();
                try {
                    Thread.currentThread().sleep(500);//阻断0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isSuccess)
                {
                    Toast.makeText(ShowProduct.this, "购买成功！", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ShowProduct.this, "购买失败！" +"余额不足.....", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private class RequestThread_insert implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/user/insertorder.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", account));
            params.add(new BasicNameValuePair("time",time));
            params.add(new BasicNameValuePair("pid",String.valueOf(product.getId())));
            params.add(new BasicNameValuePair("number",String.valueOf(count)));
            params.add(new BasicNameValuePair("totalprice",String.valueOf(totalprice)));

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
