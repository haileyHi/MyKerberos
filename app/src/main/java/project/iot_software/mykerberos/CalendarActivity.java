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
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    static boolean isSelected;
    static String selectedDay;
    SimpleDateFormat dateFormat;

    private ListView listView; //동영상 목록을 담은 리스트뷰
    //    ArrayList fileArrayList = new ArrayList<String>();//파이어베이스 DB의 파일 목록 저장하기 위한 arraylist
    List<String> fileArray = new ArrayList<String>();
    List<String> fileOfTheDate = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
//    ListViewAdapter adapter;

    private MyVideoView videoView;
    Bitmap thumbnail; //썸네일이 필요하다면 이것 사용하기.
    static boolean hasBeenCalled = false; //재생된 적 있는 비디오인지 판별
    ProgressDialog progressDialog;

    FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
    DatabaseReference dReference;
    ChildEventListener childEListener;

    FirebaseStorage fStorage;// 파이어베이스 저장소 객체
    StorageReference storageReference;
    String clickedFile; //클릭한 리스트 아이템 확인
    Map<String, String> filenameAndURL = new HashMap<>();
    Map<String, Bitmap> filenameAndThumbnail = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //저장소 접근 위한 객체 생성
        if(!hasBeenCalled){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            hasBeenCalled = true;
        }

        //액션바 메뉴
//        getSupportActionBar().setTitle("Door");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));

        listView = (ListView) findViewById(R.id.listView_file);
        videoView = (MyVideoView) findViewById(R.id.videoView);
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
                    fileDate.nextToken();//앞에 붙은 비디오 제거
                    trimmedDate = fileDate.nextToken(); // 두번째 날짜 가져다가 스트링에 저장
                    trimmedTime = fileDate.nextToken();

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
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fileOfTheDate); //R.layout.video_list_item
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fileArray);
//        adapter = new ListViewAdapter(this,);           //adapter를 어레이 어댑터 아닌 listview adapter사용할때의 코드임
        listView.setAdapter(adapter);


        //push notification 위한 빌더 생성
//        NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
//        Intent intent1 = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class);

        //DB 정보 읽어오기
        dReference = fDatabase.getReference("videos");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear(); //리스트뷰 내용 초기화

                for(DataSnapshot detectionInfo : dataSnapshot.getChildren()){

                    String contents = detectionInfo.getValue().toString();
                    StringTokenizer stringTokenizer = new StringTokenizer(contents,"\"",false); //{\"filename\" : \"파일명\", \"fileURL\" : \"파일경로\"}
                    stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    String filename = stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    stringTokenizer.nextToken();
                    String fileURL = stringTokenizer.nextToken();
                    //썸네일 추출
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(fileURL, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 360, 480);
                    //이 값을 읽어서 custom list item 아이디에 잘 넣어주어야 함.
                    fileArray.add(/*time + " : " + */filename);
                    filenameAndURL.put(filename,fileURL);
                    filenameAndThumbnail.put(filename, thumbnail);

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
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filenameAndURL.get("video_"+listDate+"_"+listTime)));
            startActivity(intent);
        });

        /*storageReference.child(" ").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("Download: ", uri.toString());

                //비디오뷰 비디오 재생
                videoView.setMediaController(new MediaController(MainActivity.this));
                videoView.setVideoURI(uri);
                videoView.requestFocus();
                videoView.start();

                //비디오 재생전 로딩 프로그레스 다이얼로그
                progressDialog = ProgressDialog.show(MainActivity.this, "잠시만 기다려주세요 ...","대기 중",true);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //실패
            }
        });*/
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
                NotificationManager notificationManager = (NotificationManager) CalendarActivity.this.getSystemService(CalendarActivity.this.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(CalendarActivity.this.getApplicationContext(), MainActivity.class);

                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(CalendarActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.drawable.ic_notifications_active_yellow_24dp).setTicker("HETT").setWhen(System.currentTimeMillis())
                        .setNumber(1).setContentTitle("우리집 케르베로스").setContentText("누군가의 방문이 감지됐어요!")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
                notificationManager.notify(1, builder.build());
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
