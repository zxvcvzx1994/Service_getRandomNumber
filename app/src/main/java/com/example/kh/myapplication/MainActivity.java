package com.example.kh.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kh.myapplication.Service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="vo cong vinh main: " ;
    private Intent intentService;
    private ServiceConnection serviceConnection;
    private boolean mIsBound ;
    private MyService myService;
    @BindView(R.id.txtBind)
    TextView txtBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intentService = new Intent(this, MyService.class);
    }

    @OnClick(R.id.btnStartService)
    public void startService(){
        startService(intentService);
    }
    @OnClick(R.id.btnStopService)
    public void stopService(){
        stopService(intentService);
    }

    @OnClick(R.id.btnBindService)
    public void bindService(){
        binServiceRandom();
    }

    @OnClick(R.id.btnUnBinService)
    public void unBindService(){
        unBindServiceRandom();
    }
    @OnClick(R.id.btnGetRandomNumber)
    public void getRandomNumberbtn(){
       getRandomNumber();
    }



    public void binServiceRandom(){
        if(serviceConnection==null)
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBindService iBindService = (MyService.MyBindService) service;
                myService = iBindService.getMyService();
                mIsBound  =true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIsBound = false;
            }
        };
        bindService(intentService,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindServiceRandom(){
        if(mIsBound){
            unbindService(serviceConnection);
            mIsBound=false;
        }
    }

    public void getRandomNumber(){
        if(mIsBound)
      txtBind.setText("Random number is: "+ myService.getRandomNumber());
        else{
            txtBind.setText("Service not bound");
        }
    }
}
