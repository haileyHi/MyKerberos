package project.iot_software.mykerberos.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import project.iot_software.mykerberos.R;

public class PasswordSetting extends AppCompatActivity {
    int countPassword = 0;
    String inputPassword = "";
    String firstInputPassword;
    String secondInputPassword;
    int tried = 1;
    Vibrator vibrator;
    String SHARED_PREF_PASSWORD = "2000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        TextView edit = (TextView)findViewById(R.id.edit);

        SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
    }
    public void one(View v) throws InterruptedException{
        inputPassword += "1";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void two(View v) throws InterruptedException{
        inputPassword += "2";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);

        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void three(View v) throws InterruptedException{
        inputPassword += "3";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void four(View v) throws InterruptedException{
        inputPassword += "4";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void five(View v) throws InterruptedException{
        inputPassword += "5";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }public void six(View v) throws InterruptedException{
        inputPassword += "6";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }public void seven(View v) throws InterruptedException{
        inputPassword += "7";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void eight(View v) throws InterruptedException{
        inputPassword += "8";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void nine(View v) throws InterruptedException{
        inputPassword += "9";
        countPassword++;
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }
    public void zero(View v) throws InterruptedException{
        inputPassword += "0";
        countPassword++;
        Log.d("lengthtest", "" + inputPassword.length());
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setText(inputPassword);
        if(tried == 1 && countPassword == 4){
            firstInputPassword = inputPassword;
            TextView tv = (TextView)findViewById(R.id.tv);
            edit.setText(inputPassword);
            tv.setText("한 번 더 입력해주세요");
            inputPassword = "";
            countPassword = 0;
            tried++;
        }
        else if(tried == 2 && countPassword == 4){
            secondInputPassword = inputPassword;
            if(firstInputPassword.equals(secondInputPassword)){
                SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SHARED_PREF_PASSWORD, firstInputPassword);
                editor.commit();
                Toast.makeText(getApplicationContext(),"비밀번호 설정이 완료되었습니다",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                TextView tv = (TextView)findViewById(R.id.tv);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                inputPassword = "";
                countPassword = 0;
                firstInputPassword = "";
                secondInputPassword = "";
                tried = 1;
            }
        }
    }

    public void backspace(View v){
        if(inputPassword.length() > 0) {
            inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
            countPassword--;
            TextView edit = (TextView)findViewById(R.id.edit);
            edit.setText(inputPassword);
        }
    }
}
