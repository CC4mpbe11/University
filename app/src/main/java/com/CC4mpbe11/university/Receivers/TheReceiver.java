package com.CC4mpbe11.university.Receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class TheReceiver extends BroadcastReceiver {
    static int notificationID;
    String assessmentNotifID = "assessmentNotifID";
    String courseNotifID = "courseNotifID";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        if(intent.getStringExtra("alarmType").equals("assessmentStart")){
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("assessmentNotifID", "Assessment Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Assessment is beginning.");
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder nb = notificationHelper.getAssessmentNotification(intent.getStringExtra("whichAssessment") + " Alert", intent.getStringExtra("whichAssessment") + " is starting.");
            notificationHelper.getManager().notify(notificationID++, nb.build());
        }
        if(intent.getStringExtra("alarmType").equals("courseStart")){
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("courseNotifID", "Course Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Course is beginning.");
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder nb1 = notificationHelper.getCourseNotification(intent.getStringExtra("whichCourse") + " Alert", intent.getStringExtra("whichCourse") + " is beginning.");
            notificationHelper.getManager().notify(notificationID++, nb1.build());
        }
        if(intent.getStringExtra("alarmType").equals("courseEnd")){
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("courseNotifID", "Course Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Course is ending.");
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder nb2 = notificationHelper.getCourseNotification(intent.getStringExtra("whichCourse") + " Alert", intent.getStringExtra("whichCourse") + " is ending.");
            notificationHelper.getManager().notify(notificationID++, nb2.build());
        }
  }

}

