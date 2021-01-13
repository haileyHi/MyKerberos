package project.iot_software.mykerberos.ui.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.calendar.CalendarActivity;

public class CertifiedActivity extends AppCompatActivity implements ActionMode.Callback {
    private static final String TAG = "MainActivity";
    private ListView listView; //동영상 목록을 담은 리스트뷰
    List<String> fileArray = new ArrayList<>();
    Map<String, String> selectedFiles = new HashMap<>();
    Set selectedArrayList = new HashSet();
//    private ArrayAdapter<String> adapter;
    private SelectionAdapter adapter;
    private VideoView videoView;

    private ActionMode actionMode;
    private CheckBox checkBox;

    Map<String, String> keyValue = new HashMap<>();//키 값 담기.

    Bitmap thumbnail; //썸네일이 필요하다면 이것 사용하기.
    static boolean hasBeenCalled = false; //재생된 적 있는 비디오인지 판별
    ProgressDialog progressDialog;

    FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
    DatabaseReference dReference;

    String clickedFileName; //클릭된 파일명을 저장하는 변수
    Map<String, String> fileNameAndURL = new HashMap<>(); //파일이름을 key로, 영상 url을 value로 하는 맵

    String serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certified);

        //상태바 글자 색 변경
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


        listView = (ListView) findViewById(R.id.listView_file);

        //리스트뷰에 뿌려줄 아이템 정보와 adapter 연결
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, fileArray); //R.layout.info_list_item
//        listView.setAdapter(adapter);
        //파일명 + 체크박스의 커스텀 리스트뷰
        adapter = new SelectionAdapter(this, R.layout.video_list_item, R.id.video_title, fileArray);// R.layout.video_list_item
        listView.setAdapter(adapter);

        //DB 정보 읽어오기
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("videos");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear(); //리스트뷰 내용 초기화

                for (DataSnapshot detectionInfo : dataSnapshot.getChildren()) {
//                    View header = layoutInflater.inflate(R.layout.header_view, null, false);

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedFileName = adapter.getItem(position);//((TextView) view).getText().toString();
                videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setMediaController(new MediaController(CertifiedActivity.this));
                videoView.setVideoURI(Uri.parse(fileNameAndURL.get(clickedFileName)));

                videoView.requestFocus();
                videoView.start();
                // 비디오재생전 로딩 프로그레스 다이아로그
                progressDialog = ProgressDialog.show(CertifiedActivity.this, "조금만 기다려주세요...", "동영상을 받아오고 있습니다...", true);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //롱 클릭시에만 멀티 초이스 가능하도록 하기.
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
                    private int nr = 0;

                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        if (checked) {
                            nr++;
                            adapter.setNewSelection(position, checked);
//                            selectedArrayList.add(adapter.getItem(position));
                            selectedFiles.put(adapter.getItem(position),adapter.getItem(position));
//                            Log.d("ssss", adapter.getItem(position));
                        }else {
                            nr--;
                            adapter.removeSelection(position);
//                            selectedArrayList.remove(adapter.getItem(position));
                            selectedFiles.remove(adapter.getItem(position));
//                            Log.d("ssss", adapter.getItem(position));
                        }
                        mode.setTitle(nr + "개 선택됨");
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        nr = 0;
                        MenuInflater inflater = getMenuInflater();
                        inflater.inflate(R.menu.contextual_menu,menu);
                        getSupportActionBar().hide();
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.video_delete: //db에서 삭제하기
                                selectedArrayList = selectedFiles.keySet();
                                AlertDialog.Builder builder = new AlertDialog.Builder(CertifiedActivity.this); //Activity 이름.this
                                builder.setTitle("삭제")
                                        .setMessage("영상" +nr + "개를 삭제 할까요?")
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                for(Object o : selectedArrayList){
                                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                    DatabaseReference databaseReference = firebaseDatabase.getReference("videos/" + keyValue.get(o.toString()));
                                                    databaseReference.removeValue();
                                                    adapter.remove(adapter.getItem(position));
                                                    adapter.notifyDataSetChanged();
                                                }
                                                nr = 0;
                                                adapter.clearSelection();
                                                selectedFiles = new HashMap<>();//저장된 선택 파일 비우기
                                                selectedArrayList = new HashSet();
                                                mode.finish();
                                            }
                                        })
                                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;
                            case R.id.video_share: //카카오톡으로 공유하기
                                //선택 영상 String에 담아서 전송 할 수 있게. 사이는 "\n\n"으로 구분
                                selectedArrayList = selectedFiles.keySet();
                                String sendUrl = "";
                                for(Object o : selectedArrayList){
                                    sendUrl = sendUrl + "\n\n" + fileNameAndURL.get(o.toString());
                                }
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, sendUrl);
                                intent.setPackage("com.kakao.talk");
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                                nr = 0;
                                adapter.clearSelection();
                                selectedFiles = new HashMap<>();//저장된 선택 파일 비우기
                                selectedArrayList = new HashSet();
                                mode.finish();
                                break;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        adapter.clearSelection();
                    }
                });
                return true;
            }
        });

        //클라우드 메시지 전송을 위한 토큰 구하기. 핸드폰 기기 고유의 토큰 값을 넣어줘야 정상적으로 작동한다.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Log", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Log", "FCM 토큰: " + token);//이 토큰을 라즈베리 파이 기기의 fcm 토큰 값에 넣어줘야 기기-앱 간의 푸시 알림을 수신할 수 있다.
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
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

    public void toCalendar(View v) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_menu,menu);
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("영상이 선택 되었어요.");
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
