package project.iot_software.mykerberos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EmergencyCall extends AppCompatActivity {
    public static final String SHARED_PREF_PHONENUMBER = "3000";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백 버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        textView = (TextView) findViewById(R.id.setPhoneNo);
        SharedPreferences pref = getSharedPreferences("emergencyCall", Context.MODE_PRIVATE);
        String pNum = pref.getString(SHARED_PREF_PHONENUMBER, "112");
        textView.setText(pNum);
    }
    public void emergencyCallSetting(View v){

        EditText et = (EditText)findViewById(R.id.phonenumber);
        String callNumber = et.getText().toString();
        if(callNumber.equals("")){
            callNumber = "112";
        }
        SharedPreferences sharedPref = getSharedPreferences("emergencyCall", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_PREF_PHONENUMBER, callNumber);
        editor.apply();
        finish();
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
}
