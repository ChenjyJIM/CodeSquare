package com.example.a54545.phpconnectnew;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
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


public class ShowAmountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_amount);
        final List<Amount> amountlist=new ArrayList<Amount>();
        final List<String> list=new ArrayList<String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpGet httpGet = new HttpGet("http://139.224.135.139:81/admin/getOrder.php");
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
                        JSONArray order=new JSONArray(result);
                      //  System.out.println(result);
                        for(int i=0;i<order.length();i++){
                            JSONObject temp=(JSONObject)order.get(i);
                            Amount amount=new Amount(temp.getString("P_name"),temp.getInt("P_price"),temp.getInt("O_number"),temp.getInt("O_tprice"));
                            amountlist.add(amount);
                            list.add(amount.toString());
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
        ListView amountlistview=(ListView)findViewById(R.id.amountlist);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        amountlistview.setAdapter(adapter);

    }
}
