package com.example.reservationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SurveillanceService : Service() {
    val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser
    val database = FirebaseDatabase.getInstance()
    val myRefReservation = database.getReference("ReservationList")
    var userID = ""
    var isSent = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intent = Intent(this, MainActivity::class.java)
        val pedingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_NO_CREATE)

        // 노티피케이션 설정 부분
        val channelId = "fcm_default_channel"
        var builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("대기 번호 확인 중")
            .setContentText("5팀 이하로 남으면 알려드립니다.")
            .setContentIntent(pedingIntent)

        var notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId,"name", NotificationManager.IMPORTANCE_DEFAULT)

        notiManager.createNotificationChannel(channel)
        notiManager.notify(0, builder.build())


        //////////
        myRefReservation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(user != null && user!!.email != null){
                    userID = user!!.email!!

                    if(isSent == false && dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java) != null && dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java)!! <= 5){
                        isSent = true;
                        val channelId = "fcm_default_channel"
                        var builder2 = NotificationCompat.Builder(applicationContext, channelId)
                        builder2.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(userID)
                            .setContentText("곧 입장할 예정입니다.")
                            .setContentIntent(pedingIntent)

                        var notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        val channel2 = NotificationChannel(channelId,"name2", NotificationManager.IMPORTANCE_DEFAULT)

                        notiManager.createNotificationChannel(channel2)
                        notiManager.notify(0, builder2.build())
                    }
                }
                else {
                    Log.d("zxcv","유저 정보 없음")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })
        //////////

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    // 이메일에서 @를 없애고 AT으로 교체, .을 없애고 DOT으로 교체
    fun removeAt(userID : String) : String{
        var returnString = ""
        for((index, value) in userID.withIndex()){
            if(value != '@' && value != '.'){
                returnString += value
            }
            else if(value == '.'){
                returnString += "DOT"
            }
            else{
                returnString += "AT"
            }
        }
        return returnString
    }
}