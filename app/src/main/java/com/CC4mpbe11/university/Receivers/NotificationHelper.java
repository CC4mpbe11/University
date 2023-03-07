package com.CC4mpbe11.university.Receivers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

import com.CC4mpbe11.university.R;

public class NotificationHelper extends ContextWrapper {
    public static final String assessmentNotifID = "assessmentNotifID";
    public static final String courseNotifID = "courseNotifID";

    private NotificationManager aManager;

    public NotificationHelper(Context base) {
        super(base);
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }*/
    }

    /*@TargetApi(Build.VERSION_CODES.O)
    public void createChannels (){
    }*/

    public NotificationManager getManager() {
        if (aManager == null){
            aManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return aManager;
    }


    public NotificationCompat.Builder getAssessmentNotification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), assessmentNotifID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.owl);
    }

    public NotificationCompat.Builder getCourseNotification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), courseNotifID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.owl);
    }
}
