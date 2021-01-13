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
import project.iot_software.mykerberos.ui.home.HomeActivity;
import project.iot_software.mykerberos.ui.setting.PasswordSetting;

public class BeforeSetPassword extends AppCompatActivity implements View.OnClickListener {
    int countPassword = 0;
    String inputPassword = "";
    String SHARED_PREF_PASSWORD = "2000";
    Vibrator vibrator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_set_password);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        backspace.setOnClickListener(this);
    }

    public void pressNum(int n) {
        inputPassword += n;
        countPassword++;
        TextView edit = (TextView) findViewById(R.id.pwedit);
        edit.setText(inputPassword);

        if (countPassword == 4) {
            SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
            if (inputPassword.equals(p)) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            } else {
                TextView tv = (TextView) findViewById(R.id.pw);
                tv.setText("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                vibrator.vibrate(500);
                Log.d("inputpassword", inputPassword);
                edit.setText(inputPassword);
                inputPassword = "";
                countPassword = 0;
            }
        }
    }

    public void backspace2(View v) {
        if (inputPassword.length() > 0) {
            inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
            countPassword--;
            TextView edit = (TextView) findViewById(R.id.pwedit);
            edit.setText(inputPassword);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == one.getId()) {
            pressNum(1);
        } else if (v.getId() == two.getId()) {
            pressNum(2);
        } else if (v.getId() == three.getId()) {
            pressNum(3);
        } else if (v.getId() == four.getId()) {
            pressNum(4);
        } else if (v.getId() == five.getId()) {
            pressNum(5);
        } else if (v.getId() == six.getId()) {
            pressNum(6);
        } else if (v.getId() == seven.getId()) {
            pressNum(7);
        } else if (v.getId() == eight.getId()) {
            pressNum(8);
        } else if (v.getId() == nine.getId()) {
            pressNum(9);
        } else if (v.getId() == zero.getId()) {
            pressNum(0);
        } else {
            if (inputPassword.length() > 0) {
                inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
                countPassword--;
                TextView edit = (TextView) findViewById(R.id.pwedit);
                edit.setText(inputPassword);
            }
        }
    }
}
