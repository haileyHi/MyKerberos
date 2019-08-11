package project.iot_software.mykerberos;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class BroadcastD extends BroadcastReceiver{
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;


    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을 때 onReceive를 호출함.
        //NotificationManager 안드로이드 상태바에 메세지를 던지기 위한 서비스 불러오고
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, new Intent(context, project.iot_software.mykerberos.MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        /*Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 55, 0);*/
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notifications_active_yellow_24dp).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("우리집 케르베로스").setContentText("누군가의 방문이 감지 되었어요!")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent).setAutoCancel(true);

        notificationManager.notify(1,builder.build());
    }
}
