package project.iot_software.mykerberos.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.calendar.ListViewAdapter;
import project.iot_software.mykerberos.ui.calendar.ListViewItem;

public class SerialNumberCheckActivity extends AppCompatActivity {
    TextView tv;
    String serialNumber;

    public static final String SHARED_PREF_YEAR = "7000";
    public static final String SHARED_PREF_MONTH = "8000";
    public static final String SHARED_PREF_DATE = "9000";

    ArrayList<ListViewItem> data;
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_number);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백 버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        SharedPreferences sharedPref = getSharedPreferences("date", Context.MODE_PRIVATE);
        int year = sharedPref.getInt(SHARED_PREF_YEAR, 1900);
        int month = sharedPref.getInt(SHARED_PREF_MONTH,1);
        int day = sharedPref.getInt(SHARED_PREF_DATE, 1);

        tv = (TextView)findViewById(R.id.serialno);
        data = new ArrayList<>();

        FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
        DatabaseReference dReference;
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("serial");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot detectionInfo : dataSnapshot.getChildren()){
                    serialNumber = detectionInfo.getValue().toString();
                    tv.setText(serialNumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed to read value",Toast.LENGTH_LONG).show();
            }
        });
        data.add(new ListViewItem("제품 코드"," "/* + serialNumber*/));

        data.add(new ListViewItem("제품 보증 기간", year + "년 " + month + "월 " + day +"일 "));
        adapter = new ListViewAdapter(data);

        listView = findViewById(R.id.info_listview);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //확장성을 위해서 case문으로 작성
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
