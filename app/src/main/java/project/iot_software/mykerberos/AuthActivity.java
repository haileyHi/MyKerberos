package project.iot_software.mykerberos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {
    public static final String SHARED_PREF_FIRST_USER_KEY = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        TextView tvHello = (TextView) findViewById(R.id.tv_hello);
        EditText et = (EditText)findViewById(R.id.edit);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int firstUser = sharedPref.getInt(SHARED_PREF_FIRST_USER_KEY, -1);

        if(firstUser == 2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (firstUser == 1 /*&& isCertified*/) {
            tvHello.setText("기존 호갱님 시리얼 번호를 입력해주세요");
        }
        else if (firstUser == -1) {
            tvHello.setText("최초 고객님");
            saveUserIsNotFirst();
        }

    }


    private void saveUserIsNotFirst() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SHARED_PREF_FIRST_USER_KEY, 1);
        editor.commit();
    }


    public void click(View v) {
        EditText et = (EditText)findViewById(R.id.edit);
        String text = et.getText().toString();
        /*SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CERTIFIED, text);
        editor.commit();*/
        TextView tv = (TextView)findViewById(R.id.tv_hello);
        if(text.equals("123456")){
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(SHARED_PREF_FIRST_USER_KEY, 2);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            tv.setText("시리얼번호가 일치하지 않습니다");
        }

    }

}
