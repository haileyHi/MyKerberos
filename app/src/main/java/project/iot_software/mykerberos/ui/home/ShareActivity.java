package project.iot_software.mykerberos.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import project.iot_software.mykerberos.R;

public class ShareActivity extends AppCompatActivity {
    private ListView listView; //동영상 목록을 담은 리스트뷰
    List<String> fileArray = new ArrayList<>();
    Map<String, String> keyValue = new HashMap<>();
    private ArrayAdapter<String> adapter;

    FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
    DatabaseReference dReference;

    String clickedFileName; //클릭된 파일명을 저장하는 변수
    Map<String, String> fileNameAndURL = new HashMap<>(); //파일이름을 key로, 영상 url을 value로 하는 맵

    String serialNumber;

    public static final String SHARED_DELETED_FILES = "4000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백 버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb6);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("serial");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot detectionInfo : dataSnapshot.getChildren()) {
                    serialNumber = detectionInfo.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to read value", Toast.LENGTH_LONG).show();
            }
        });


        listView = (ListView) findViewById(R.id.lvlv);

        //리스트뷰에 뿌려줄 아이템 정보와 adapter 연결
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fileArray); //R.layout.video_list_item

        listView.setAdapter(adapter);

        //DB 정보 읽어오기
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("videos");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear(); //리스트뷰 내용 초기화
                for (DataSnapshot detectionInfo : dataSnapshot.getChildren()) {

                    //raspberry에서 firebase로 올라갈때 {\"filename\" : \"파일명\", \"fileURL\" : \"파일경로\"}인 경우와 {\"fileURL\" : \"파일경로\", \"filename\" : \"파일명\"} 인 경우 고려학기
                    String firstKey, firstValue, secondValue;
                    String filename, fileURL;

                    String contents = detectionInfo.getValue().toString();
                    StringTokenizer stringTokenizer = new StringTokenizer(contents, "\"", false); //{\"filename\" : \"파일명\", \"fileURL\" : \"파일경로\"}
                    stringTokenizer.nextToken();            //    {
                    firstKey = stringTokenizer.nextToken(); //    filename  || fileURL     - firstkey가 아닌 게 secondKey이므로 secondKey는 별도로 저장할 필요없음.
                    stringTokenizer.nextToken();            //    :
                    firstValue = stringTokenizer.nextToken();
                    stringTokenizer.nextToken();            //    ,
                    stringTokenizer.nextToken();            //    fileURL   || filename
                    stringTokenizer.nextToken();            //    :
                    secondValue = stringTokenizer.nextToken();
                    if(firstKey.equals("filename")) {
                        filename = firstValue;
                        fileURL = secondValue;
                    }else {
                        filename = secondValue;
                        fileURL = firstValue;
                    }

                    fileArray.add(filename);
                    keyValue.put(filename, detectionInfo.getKey());
                    fileNameAndURL.put(filename, fileURL);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to read value", Toast.LENGTH_LONG).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                clickedFileName = ((TextView) view).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
                builder.setTitle("공유")
                        .setMessage("이 영상을 공유하시겠습니까?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String message = fileNameAndURL.get(adapter.getItem(position));
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, message);
                                intent.setPackage("com.kakao.talk");

                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
