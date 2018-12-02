package com.example.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Handler mainHandler   =  new Handler(){
        @Override
        public void handleMessage(Message msg){
            Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
        }
    };
    Handler subHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 1、创建Looper对象，然后创建了MessageQueue
                 * 2、将当前的Looper对象跟当前的线程（子线程）绑定ThreadLocal
                 * */
                Looper.prepare();

                subHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg){
                        Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
                    }
                };
                /**
                 * 1、从当前线程获取之前创建的Looper对象，然后找到MeaagaeQueue
                 * 2、开启死循环，遍历消息池的对象
                 * */

                /**
                 * 1、从当前线程获取之前创建的Looper对象，然后找到MeaagaeQueue
                 * 2、开启死循环，遍历消息池的对象
                 * */
                Looper.loop();
            }
        }).start();

    }

    public void sendMsg(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Message msg = new Message();
                msg.what =1;
                msg.obj = "来自子线程的问候";

                mainHandler.sendMessage(msg);
            }
        }).start();
    }

    public void sendMsgFromUi(View view){
        subHandler.obtainMessage(2,"我是主线程").sendToTarget();
    }
}
