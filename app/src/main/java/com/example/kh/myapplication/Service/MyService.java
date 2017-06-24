package com.example.kh.myapplication.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by kh on 6/24/2017.
 */

public class MyService extends Service{
    private int randomNumber;
    private final int MAX=100;
    private final int MIN=1;
    private boolean mIsGeneratorOn;

   public  class MyBindService extends Binder{
        public  MyService getMyService(){
            return MyService.this;
        }
    }

    private  IBinder iBinder = new MyBindService();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: "+Thread.currentThread().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumer();

            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startRandomNumer(){
        mIsGeneratorOn=true;
        while(mIsGeneratorOn){
            try {
                Thread.sleep(2000);
                if(mIsGeneratorOn){
                    randomNumber = new Random().nextInt(MAX)+MIN;

                    Log.i(TAG, "randomNumer: "+randomNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void stopRandomNumber(){
        mIsGeneratorOn =false;
    }

    public  int getRandomNumber(){
        return randomNumber;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumber();
        Log.i(TAG, "onDestroy: ");
    }




}
