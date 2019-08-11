package project.iot_software.mykerberos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;

    private Button realtimeCamButton;
    private ImageButton  doorCheckButton;
    private MaterialButton calendarButton;
    private FloatingActionButton fab_siren, fab_call, fab_text;
    private Animation fab_open,fab_close;
    private boolean isFabOpen = false;

    //달력 선택 시 해당 요일에 촬영된 영상 확인 가능
    private static MaterialCalendarView materialCalendarView;

    static CalendarDay selectedDay = null;
    static boolean isSelected;

    //


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        mContext = getApplicationContext();

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        calendarButton = (MaterialButton) findViewById(R.id.checkCalendar);
        realtimeCamButton = (Button) findViewById(R.id.realtimeCam);
        doorCheckButton = (ImageButton) findViewById(R.id.doorCheck);
        calendarButton.setOnClickListener(this);
        realtimeCamButton.setOnClickListener(this);
        doorCheckButton.setOnClickListener(this);

        fab_siren = (FloatingActionButton) findViewById(R.id.fabSiren);
        fab_call = (FloatingActionButton) findViewById(R.id.fabCall);
        fab_text = (FloatingActionButton) findViewById(R.id.fabText);
        fab_siren.setOnClickListener(this);
        fab_call.setOnClickListener(this);
        fab_text.setOnClickListener(this);

        Toast.makeText(this,"메인 액티비티 실행",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkCalendar:
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                finish();
                break;
            case R.id.fabSiren:
                toggleFab();
                break;
            case R.id.fabCall:
                toggleFab();
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/010-8536-8243"));
                startActivity(callIntent);
                break;
            case R.id.fabText:
                toggleFab();
                //말풍선 띄워서 메시지 입력 후 카톡으로 보내기
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //Activity 이름.this
                builder.setTitle("카카오톡으로 보내기");

                final EditText text = new EditText(MainActivity.this);//액티비티 이름.this
                builder.setView(text); //에딧 텍스트 추가해서 빌더 생성

                Intent textIntent = new Intent(Intent.ACTION_SEND);

                builder.setPositiveButton("보내기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = text.getText().toString();

                        textIntent.setType("text/plain");
                        textIntent.putExtra(Intent.EXTRA_TEXT,message);
                        textIntent.setPackage("com.kakao.talk");
                        startActivity(textIntent);

                        dialog.dismiss();
                    }

                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

                break;
        }
    }

    private void toggleFab(){
        if(isFabOpen){
            fab_siren.setImageResource(R.drawable.ic_notifications_active_yellow_24dp);
            fab_call.startAnimation(fab_close);
            fab_text.startAnimation(fab_close);
            fab_call.setClickable(false);
            fab_text.setClickable(false);
            isFabOpen = false;
        }else {
            fab_siren.setImageResource(R.drawable.ic_close_black_24dp);
            fab_call.startAnimation(fab_open);
            fab_text.startAnimation(fab_open);
            fab_call.setClickable(true);
            fab_text.setClickable(true);
            isFabOpen = true;
        }
    }
}

