package com.example.a54545.phpconnectnew.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a54545.phpconnectnew.R;

/**
 * 商家入口：店铺数据分析界面，使用手势操作滑动来改变图片。
 */
public class DataActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btn1;
    private int[] resId = new int[]{
            R.drawable.cake,R.drawable.datawant,R.drawable.waster
    };
    private GestureDetector gestureDetector;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page);
        imageView=(ImageView)findViewById(R.id.imageView);
        btn1=(Button)findViewById(R.id.button11);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataActivity.this,WifiActivity.class);
                startActivity(intent);
            }
        });
        gestureDetector = new GestureDetector(onGestureListener);   //设置手势监听由onGestureListener处理
    }
    public boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    private GestureDetector.OnGestureListener onGestureListener
            = new GestureDetector.SimpleOnGestureListener(){
        //当识别的手势是滑动手势时回调onFinger方法
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
            //得到手触碰位置的起始点和结束点坐标 x , y ，并进行计算
            float x = e2.getX()-e1.getX();
            float y = e2.getY()-e1.getY();
            //通过计算判断是向左还是向右滑动
            if(x > 0){
                count++;
                count%=(resId.length-1);        //想显示多少图片，就把定义图片的数组长度-1
            }else if(x < 0){
                count--;
                count=(count+(resId.length-1))%(resId.length-1);
            }
            imageView.setImageResource(resId[count]);  //切换imageView的图片
            return true;
        }
    };
}
