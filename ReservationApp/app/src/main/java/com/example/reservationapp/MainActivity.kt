package com.example.reservationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reservationapp.DbStructure.UserInfo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW))
        }
        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        Log.d(TAG, "Subscribing to reservation topic")
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("reservation")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]

        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
        // [END retrieve_current_token]

        newActivity.setOnClickListener{
            user = mAuth.currentUser
            if (user != null) {
                val intent = Intent(this, QrReservationActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(baseContext, "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        button_login.setOnClickListener {
            user = mAuth.currentUser
            if (user != null) {
                FirebaseAuth.getInstance().signOut()
                textView_showEmail.text = "사용자 정보 없음"
                button_login.text = "로그인"
                Toast.makeText(baseContext, "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        user = mAuth.currentUser
        var id : String?

        if (user != null) {
            button_login.text = "로그아웃"
            id = user!!.email;
            textView_showEmail.text = id
        }
        else{
            button_login.text = "로그인"
            textView_showEmail.text = "사용자 정보 없음"
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}

