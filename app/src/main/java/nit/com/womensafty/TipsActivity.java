package nit.com.womensafty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
