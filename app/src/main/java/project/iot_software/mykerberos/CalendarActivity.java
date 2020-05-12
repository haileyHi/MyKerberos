package project.iot_software.mykerberos;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CalendarActivity extends AppCompatActivity{
    //달력 선택 시 해당 요일에 촬영된 영상 확인 가능
    private static MaterialCalendarView materialCalendarView;

    //    static CalendarDay selectedDay = null;
    static String selectedDay;
    SimpleDateFormat dateFormat;

    private ListView listView; //동영상 목록을 담은 리스트뷰
    //    ArrayList fileArrayList = new ArrayList<String>();//파이어베이스 DB의 파일 목록 저장하기 위한 arraylist
    List<String> fileArray = new ArrayList<String>();
    List<String> fileOfTheDate = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
    private DatabaseReference dReference;
    ChildEventListener childEListener;

    private FirebaseStorage fStorage;// 파이어베이스 저장소 객체
    private StorageReference storageReference;
    String clickedFile; //클릭한 리스트 아이템 확인
    Map<String, String> filenameAndURL = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //백 버튼 추가
        Toolbar toolbar = (Toolbar)findViewById(R.id.tb1);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        listView = (ListView) findViewById(R.id.listView_file);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        //달력설정
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new TodayDecorator());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                adapter.clear();
                /*CalendarDay 출력 형태 CalendarDay{2019-08-07}
                 *? Date 자체의 출력 형태 Thu Aug 28 00:00:00 GMT
                 */
                selectedDay = dateFormat.format(date.getDate());
                String trimmedDate;
                String trimmedTime;

                for(int i = 0; i<fileArray.size() -1 ; i++) {
                    StringTokenizer fileDate = new StringTokenizer(fileArray.get(i),"_");
                    trimmedDate = fileDate.nextToken(); // 날짜 가져다가 스트링에 저장
                    Log.d("dateCheck", trimmedDate);
                    trimmedTime = fileDate.nextToken();//촬영 시간 스트링에 저장
                    Log.d("timeCheck", trimmedTime);

                    if (selectedDay.equals(trimmedDate)) {//달력에서 누른 날짜를 파일명에서 추출한 날짜랑 비교
                        fileOfTheDate.add(trimmedDate + " " + trimmedTime); //리스트어레이에 뽑은 날짜 이름 넣기.
//                        Toast.makeText(getApplicationContext(),selectedDay + " vs " + trimmedDate,Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        //데이터베이스
        initDatabase();//DB 초기화

        //저장소
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReferenceFromUrl("gs://mykerberos-2a68f.appspot.com");//firebase storage 주소 참조해 저장소ref 생성

        //리스트뷰에 뿌려줄 아이템 정보와 adapter 연결 (누른 date 의 값을 받아와서 해당 날짜의 영상만 보여주자)
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fileOfTheDate); //R.layout.info_list_item
        listView.setAdapter(adapter);

        //DB 정보 읽어오기
        dReference = fDatabase.getReference("videos");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear(); //리스트뷰 내용 초기화

                for(DataSnapshot detectionInfo : dataSnapshot.getChildren()){

                    //raspberry에서 firebase로 올라갈때 {\"filename\" : \"파일명\", \"fileURL\" : \"파일경로\"}인 경우와 {\"fileURL\" : \"파일경로\", \"filename\" : \"파일명\"} 인 경우 고려하기
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
                    fileArray.add(/*time + " : " + */filename);
                    filenameAndURL.put(filename,fileURL);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed to read value",Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener((parent, view,position, id) ->{
            clickedFile = ((TextView)view).getText().toString();
            StringTokenizer stringTokenizer = new StringTokenizer(clickedFile," ");
            String listDate = stringTokenizer.nextToken();
            String listTime = stringTokenizer.nextToken();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filenameAndURL.get(listDate+"_"+listTime)));
            startActivity(intent);
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

    //데이터베이스 변화 적용하기 /*참고 출처 : https://firebase.google.com/docs/database/android/lists-of-data*/
    private void initDatabase() {
        fDatabase = FirebaseDatabase.getInstance();

        dReference = fDatabase.getReference("videos");
//        dReference.child("videos").setValue("check");

        childEListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String newFile = dataSnapshot.getValue().toString();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //동영상 삭제 시 여기서 알려줄까?
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dReference.addChildEventListener(childEListener);
    }

    //액션바 추가


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
