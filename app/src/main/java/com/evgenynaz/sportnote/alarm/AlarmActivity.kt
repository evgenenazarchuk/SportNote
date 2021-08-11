package com.evgenynaz.myhomework.alarm

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.evgenynaz.sportnote.databinding.ActivityAlarmBinding


class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private var ringtone: Ringtone? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var  notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
         ringtone = RingtoneManager.getRingtone(this, notificationUri)
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(this, notificationUri)
        }
        if (ringtone != null) {
            ringtone!!.play()
        }
    }

    override fun onDestroy() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }
        super.onDestroy()
    }
}