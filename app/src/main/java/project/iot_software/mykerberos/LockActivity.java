package project.iot_software.mykerberos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LockActivity extends AppCompatActivity {
    int countPassword = 0;
    String inputPassword = "";
    String SHARED_PREF_PASSWORD = "2000";
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void one2(View v){
        inputPassword += "1";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void two2(View v){
        inputPassword += "2";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void three2(View v){
        inputPassword += "3";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void four2(View v){

        inputPassword += "4";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void five2(View v){
        inputPassword += "5";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void six2(View v){
        inputPassword += "6";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void seven2(View v){
        inputPassword += "7";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void eight2(View v){
        inputPassword += "8";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void nine2(View v){
        inputPassword += "9";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void zero2(View v){
        inputPassword += "0";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }

    public void backspace2(View v){
        if(inputPassword.length() > 0) {
            inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
            countPassword--;
            TextView edit = (TextView)findViewById(R.id.pwedit);
            edit.setText(inputPassword);
        }
    }
}
