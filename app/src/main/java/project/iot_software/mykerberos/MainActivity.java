package project.iot_software.mykerberos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.concurrent.locks.Lock;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF_FIRST_USER_KEY = "1000";
    public static final String SHARED_PREF_PASSWORD = "2000";
    String serialNumber;

    public static final String SHARED_PREF_YEAR = "7000";
    public static final String SHARED_PREF_MONTH = "8000";
    public static final String SHARED_PREF_DATE = "9000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        EditText et = (EditText)findViewById(R.id.edit);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int firstUser = sharedPref.getInt(SHARED_PREF_FIRST_USER_KEY, 2);
        Log.d("firstuser", "" + firstUser);

        FirebaseDatabase fDatabase;//파이어베이스 데이터베이스 객체
        DatabaseReference dReference;
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("serial");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot detectionInfo : dataSnapshot.getChildren()){
                    serialNumber = detectionInfo.getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed to read value",Toast.LENGTH_LONG).show();
            }
        });

        if(firstUser == 3){ //시리얼 번호 일치하는
            SharedPreferences sp = getSharedPreferences("pw", Context.MODE_PRIVATE);
            String p = sp.getString(SHARED_PREF_PASSWORD, "-1");

            if(p.equals("-1")){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }
        else if (firstUser == 1) { //최초는 아닌데
            Log.d("firstuser", ""+firstUser);
        }
        else if (firstUser == 2) {
            Log.d("firstuser", ""+firstUser);
            saveUserIsNotFirst();
            Log.d("firstuser", ""+firstUser);
        }

    }
    private void saveUserIsNotFirst() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SHARED_PREF_FIRST_USER_KEY, 1);
        editor.commit();
    }


    public void click(View v) {
        EditText et = (EditText)findViewById(R.id.edit);
        String text = et.getText().toString();


        if(text.equals(/*"123456"*/serialNumber)){
            Log.d("serialserial", serialNumber);
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(SHARED_PREF_FIRST_USER_KEY, 3);
            editor.apply();
            Date d = new Date();
            int year = d.getYear() + 1900 + 1;
            int month = d.getMonth() + 1;
            int date = d.getDate();
            SharedPreferences sp = getSharedPreferences("date", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.putInt(SHARED_PREF_YEAR, year);
            e.putInt(SHARED_PREF_MONTH, month);
            e.putInt(SHARED_PREF_DATE, date);
            e.apply();
            Log.d("datedate", ""+year+" " + month + " " + date);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
        else{
            ImageView inputImageView = (ImageView)findViewById(R.id.inputiv);
            ImageView notSameImageView = (ImageView)findViewById(R.id.notsameiv);
            inputImageView.setVisibility(View.INVISIBLE);
            notSameImageView.setVisibility(View.VISIBLE);
        }
    }

}
