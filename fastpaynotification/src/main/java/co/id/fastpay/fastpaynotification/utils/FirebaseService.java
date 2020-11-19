package co.id.fastpay.fastpaynotification.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.ui.NotificationDetailActivity;

public class FirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();

        /*
         * Cek jika notif berisi data payload
         * pengiriman data payload dapat dieksekusi secara background atau foreground
         */

        if (remoteMessage.getData().size() > 0) {
            Log.e("TAG", "Message data payload: " + remoteMessage.getData());
            showNotif(data.get("title"), data.get("message"), Integer.parseInt(data.get("inbox_id")), data.get("inbox_type"), data.get("url_image"));
        }

        /*
         * Cek jika notif berisi data notification payload
         * hanya dieksekusi ketika aplikasi bejalan secara foreground
         * dan dapat push notif melalui UI Firebase console
         */
        /*if (remoteMessage.getNotification() != null) {
            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotif(data.get("title"), data.get("message"), Integer.parseInt(data.get("inbox_id")));
        }
*/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showNotif(String title, String message, int inboxId, String inboxType, String urlImage) {

        Bundle bundle = new Bundle();
        bundle.putInt("InboxID", inboxId);
        bundle.putString("InboxType", inboxType);

        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        if (urlImage == null || urlImage.equals("")){
            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "NotifApps")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_avatar_news) // icon
                    .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
                    .setLights(200, 200, 200) // light button
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
                    .setOnlyAlertOnce(true) // set alert sound notif
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    //.setStyle(new NotificationCompat.Style.bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent); // action notif ketika di tap
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, notifBuilder.build());
        }else{
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, "NotifApps")
                            .setSmallIcon(R.drawable.ic_avatar_news).setContentTitle(title)
                            .setContentText(message).setAutoCancel(true).setShowWhen(true)
                            .setColor(ContextCompat.getColor(this, R.color.material_color_blue_700))
                            .setLights(200, 200, 2000)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setTicker("Description").setSound(Settings.System.DEFAULT_NOTIFICATION_URI).setContentIntent(pendingIntent);
            FutureTarget<Bitmap> futureTarget = Glide.with(this).asBitmap()
                    .load(urlImage).submit();
            LoadImageTask task = new LoadImageTask(icon -> {
                notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon)).setLargeIcon(icon);
                Glide.with(this).clear(futureTarget);
                notificationManager.notify(1,
                        notificationBuilder.build());
            });
            task.execute(futureTarget);
        }
    }

    private static class LoadImageTask extends AsyncTask<FutureTarget<Bitmap>, Void, Bitmap> {
        private OnSuccess onSuccess;

        interface OnSuccess {
            void onSuccess(Bitmap bitmap);
        }

        LoadImageTask(OnSuccess onSuccess) {
            this.onSuccess = onSuccess;
        }

        @SafeVarargs @Override
        protected final Bitmap doInBackground(FutureTarget<Bitmap>... futureTargets) {
            try {
                return futureTargets[0].get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null)
                onSuccess.onSuccess(bitmap);
        }
    }
}