package com.evgenynaz.sportnote.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evgenynaz.sportnote.databinding.AlarmActivityFinishBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi.toolbar_bmi_activity
import kotlinx.android.synthetic.main.alarm_activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivityFinish : AppCompatActivity() {
    private lateinit var binding: AlarmActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlarmActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()

        setSupportActionBar(toolbar_alarm_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // вернуться назад
        binding.toolbarAlarmActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.startAlarm.setOnClickListener {
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)

             //   .setHour(0)
              //  .setMinute(0)

                .setTitleText("Выберите время для будильника")
                .build()
            materialTimePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
                calendar[Calendar.MINUTE] = materialTimePicker.minute
                calendar[Calendar.HOUR_OF_DAY] = materialTimePicker.hour
                val alarmManager =
                    getSystemService(ALARM_SERVICE) as AlarmManager
                val alarmClockInfo = AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    getAlarmInfoPendingIntent()
                )

                val notifuBpouk = Intent(this, NotificationBroadcast::class.java)
                val pendingIntent1 = PendingIntent.getBroadcast(
                    applicationContext,
                    1,
                    notifuBpouk,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent1)



                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
                Toast.makeText(
                    applicationContext,
                    "Будильник установлен на " + sdf.format(calendar.time),
                    Toast.LENGTH_SHORT
                ).show()
            }

            materialTimePicker.show(supportFragmentManager, "tag_picker")
        }
// Если не работает будильник в android 10, нужно запросить разрешение на показ окон поверх других приложений
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }*/
    }

    private fun getAlarmInfoPendingIntent(): PendingIntent? {
        val alarmInfoIntent = Intent(this, AlarmActivityFinish::class.java)
        alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            this,
            0,
            alarmInfoIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getAlarmActionPendingIntent(): PendingIntent? {
        val intent = Intent(this, AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}