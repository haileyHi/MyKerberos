package project.iot_software.mykerberos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    static boolean hasBeenCalled = false; //firebasedb 호출된 적 있는지 확인. 최초 실행시에 호출되어야 함.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //SetPersistenceEnabled should be called before creating any instances of DatabaseReference, and only needs to be called once per application
        if(!hasBeenCalled){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            hasBeenCalled = true;
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
