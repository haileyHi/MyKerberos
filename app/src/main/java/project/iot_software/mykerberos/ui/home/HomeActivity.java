package project.iot_software.mykerberos.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import project.iot_software.mykerberos.R;
import project.iot_software.mykerberos.ui.calendar.CalendarActivity;
import project.iot_software.mykerberos.ui.setting.DeveloperInformation;
import project.iot_software.mykerberos.ui.setting.SerialNumberCheckActivity;
import project.iot_software.mykerberos.ui.setting.SettingsActivity;

import static project.iot_software.mykerberos.ui.home.EmergencyCall.SHARED_PREF_PHONENUMBER;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String SHARED_PREF_PASSWORD = "2000";
    private Context mContext;

    Toolbar toolbar;
    private ImageButton doorCheckButton;
    private MaterialButton calendarButton;
    private FloatingActionButton fab_siren, fab_call, fab_text;
    private Animation fab_open, fab_close, fade_in, fade_out;
    private boolean isFabOpen = false;
    long pressedTime;
    private ImageView monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
        Log.d("pwpw", p);

        int textColor = ContextCompat.getColor(this, R.color.colorAccent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(color);
        toolbar.setTitleMargin(300, 50, 50, 300);
        toolbar.setTitleTextAppearance(this, R.style.App_Title);
        toolbar.getBackground().setAlpha(0);

        monitor = findViewById(R.id.gif_monitor);
        GlideDrawableImageViewTarget gifIMage = new GlideDrawableImageViewTarget(monitor);
        Glide.with(this).load(R.drawable.monitor_display).into(gifIMage);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fab_siren = findViewById(R.id.fabSiren);
        fab_call = findViewById(R.id.fabCall);
        fab_text = findViewById(R.id.fabText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab();
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                SharedPreferences sharedPref = getSharedPreferences("emergencyCall", Context.MODE_PRIVATE);
                String p = sharedPref.getString(SHARED_PREF_PHONENUMBER, "112");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + p));
                startActivity(callIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        fab_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                //카톡으로 빠른 공유
                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("TOKENTOKEN", token);
                    }
                });
    }

    private void toggleFab() {
        if (isFabOpen) {
            fab_siren.setImageResource(R.drawable.ic_notifications_active_yellow_24dp);
            fab_call.startAnimation(fab_close);
            fab_text.startAnimation(fab_close);
            fab_call.setClickable(false);
            fab_text.setClickable(false);
            isFabOpen = false;
        } else {
            fab_siren.setImageResource(R.drawable.ic_close_black_24dp);
            fab_call.startAnimation(fab_open);
            fab_text.startAnimation(fab_open);
            fab_call.setClickable(true);
            fab_text.setClickable(true);
            isFabOpen = true;
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            if (System.currentTimeMillis() - pressedTime < 20000) {
                finishAffinity();
                return;
            }
            Toast.makeText(this, "'뒤로'버튼 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, SerialNumberCheckActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, CertifiedActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else if (id == R.id.nav_production) {
            Intent intent = new Intent(this, DeveloperInformation.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

}
