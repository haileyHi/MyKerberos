package project.iot_software.mykerberos.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.setting.PasswordSetting;

public class BeforeSetPassword extends AppCompatActivity {
    int countPassword = 0;
    String inputPassword = "";
    String SHARED_PREF_PASSWORD = "2000";
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_set_password);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Button one = findViewById(R.id.btn_one_before);
        Button two = findViewById(R.id.btn_two_before);
        Button three = findViewById(R.id.btn_three_before);
        Button four = findViewById(R.id.btn_four_before);
        Button five = findViewById(R.id.btn_five_before);
        Button six = findViewById(R.id.btn_six_before);
        Button seven = findViewById(R.id.btn_seven_before);
        Button eight = findViewById(R.id.btn_eight_before);
        Button nine = findViewById(R.id.btn_nine_before);
        Button zero = findViewById(R.id.btn_zero_before);
        ImageView backspace = findViewById(R.id.btn_backspace_before);
    }

    public void one3(View v){
        inputPassword += "1";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                Log.d("password", p);
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void two3(View v){
        inputPassword += "2";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void three3(View v){
        inputPassword += "3";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void four3(View v){
        inputPassword += "4";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void five3(View v){
        inputPassword += "5";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void six3(View v){
        inputPassword += "6";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void seven3(View v){
        inputPassword += "7";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void eight3(View v){
        inputPassword += "8";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void nine3(View v){
        inputPassword += "9";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }
    public void zero3(View v){
        inputPassword += "0";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if(countPassword == 4){
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if(inputPassword.equals(p)){
                Intent intent = new Intent(this, PasswordSetting.class);
                startActivity(intent);
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                Log.d("password", p);
                vibrator.vibrate(500);
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
