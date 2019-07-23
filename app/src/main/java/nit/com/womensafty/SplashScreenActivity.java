package nit.com.womensafty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private Thread mSplashThread;
    final SplashScreenActivity sPlashScreen = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                } catch(InterruptedException ex){
                }
                finish();
                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}