package com.example.a54545.phpconnectnew;

/**
 * Created by 54545 on 2018/3/28.
 */

        import android.content.Intent;
        import android.os.Parcelable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        final ListView listview=(ListView)findViewById(R.id.listview);
        final List<String> list=new ArrayList<String>();
        final List<Customer>customerlist=new ArrayList<Customer>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpGet httpGet = new HttpGet("http://139.224.135.139:81/admin/getAllCustomer.php");
                try {
                    //第三步：执行请求，获取服务器发还的相应对象
                    HttpResponse response = httpClient.execute(httpGet);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    if (response.getStatusLine().getStatusCode() == 200) {
                        //第五步：从相应对象当中取出数据，放到entity当中
                        HttpEntity entity = response.getEntity();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                        String result = reader.readLine();
                        JSONArray customer=new JSONArray(result);
                        for(int i=0;i<customer.length();i++){
                            JSONObject temp=(JSONObject)customer.get(i);
                            customerlist.add(new Customer(temp.getInt("C_id"),temp.getString("C_name"),temp.getString("C_password"),
                                    temp.getString("C_sex"),temp.getString("C_phone"),temp.getString("C_address"),
                                    new BigDecimal(temp.getString("C_cash")), temp.getInt("C_times"),temp.getInt("A_id")

                            ));
                            list.add(temp.getString("C_name"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.currentThread().sleep(500);//阻断0.1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
        AdapterView.OnItemClickListener listViewListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("customer",customerlist.get(position));
                Intent intent=new Intent(CustomerActivity.this,ShowCustomerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        listview.setOnItemClickListener(listViewListener);
    }
}