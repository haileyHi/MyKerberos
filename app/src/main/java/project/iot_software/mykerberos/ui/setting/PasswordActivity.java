package project.iot_software.mykerberos.ui.setting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.setting.BeforeSetPassword;
import project.iot_software.mykerberos.ui.setting.PasswordSetting;

public class PasswordActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    String SHARED_PREF_PASSWORD = "2000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //시계색 검게
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백 버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tbP);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        //비밀번호 설정 되어 있으면 라디오 버튼 체크 하게 만들기
        SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
        if(p.equals("-1")) {
            RadioButton rb = (RadioButton)findViewById(R.id.noPassword);
            rb.setChecked(true);
        }
        else{
            RadioButton rb = (RadioButton)findViewById(R.id.password);
            rb.setChecked(true);
        }

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.noPassword){
                    SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(SHARED_PREF_PASSWORD, "-1");
                    editor.commit();
                }
                else{
                    SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                    String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
                    if(p.equals("-1")) {
                        Intent intent = new Intent(getApplicationContext(), PasswordSetting.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), BeforeSetPassword.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void rb1check(View v){
        RadioButton rb = (RadioButton)findViewById(R.id.noPassword);
        rb.setChecked(true);
    }
    public void rb2check(View v){
        RadioButton rb = (RadioButton)findViewById(R.id.password);
        rb.setChecked(true);
    }
}
