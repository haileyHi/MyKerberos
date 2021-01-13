package project.iot_software.mykerberos.ui.setting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.home.EmergencyCall;

public class SettingsActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> settingList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //시계색 검게
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, settingList);
        listView.setAdapter(adapter);
        final LayoutInflater layoutInflater = LayoutInflater.from(this);

        adapter.clear();
        settingList.add("비밀번호 설정 및 변경");
        settingList.add("비상 연락망 설정");
        adapter.notifyDataSetChanged();
        listView.setSelection(adapter.getCount() - 1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String clickedFileName = ((TextView) view).getText().toString();
                if(clickedFileName.equals("비밀번호 설정 및 변경")){
                    Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
                else if(clickedFileName.equals("비상 연락망 설정")){
                    Intent intent = new Intent(getApplicationContext(), EmergencyCall.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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

}
