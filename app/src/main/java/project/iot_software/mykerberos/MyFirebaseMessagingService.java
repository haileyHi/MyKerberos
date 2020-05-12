package project.iot_software.mykerberos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String newToken = FirebaseInstanceId.getInstance().getToken();
//        sendRegistrationToServer(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage != null && remoteMessage.getData().size() > 0)
            sendNotification(remoteMessage);
//        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



        String channel_id = "MyKeroChan";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel_id);
        builder.setContentTitle("우리집 케르베로스")
                .setContentText("누군가의 방문이 감지되었어요")
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //SDK 버전이 오레오 이상이면 notification channel이 필요하다. 채널이 없으면 푸시 알림이 전송되지 않음.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelName = "notification";
            NotificationChannel notichannel = new NotificationChannel(channel_id, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notichannel);
        }
        //알람 여러개 수신하기 위한 id값 다르게 부여.
        notificationManager.notify((int) System.currentTimeMillis(),builder.build());
    }

}
