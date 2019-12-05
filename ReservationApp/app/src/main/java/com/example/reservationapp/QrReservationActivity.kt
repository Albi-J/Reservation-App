package com.example.reservationapp;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.reservationapp.DbStructure.ResInfo
import com.example.reservationapp.DbStructure.ReservationInfo
import com.example.reservationapp.DbStructure.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_database_test.*
import kotlinx.android.synthetic.main.activity_qr_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class QrReservationActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser
    var myRefReservation = database.getReference("ReservationList")
    var myRefRestaurant = database.getReference("RestaurantList")
    var myRefUser = database.getReference("UserList")

    var resName : String = ""
    var userID : String = ""
    var waitNum : Int = -1
    var resTime : String = ""

    var condition = 1


    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reservation)
        initScan()

        val serviceIntent = Intent(this, SurveillanceService::class.java)
        startService(serviceIntent)

        cancle_reservation.setOnClickListener {

            myRefReservation.child(removeAt(userID)).removeValue()

            // TODO
            // 식당에서 번호 하나 빼야 됩니다.

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        button_control.setOnClickListener {
            Log.d("zxcv", "강제 실행")
            afterLoadDataFromQrCode("낙원 돈까스")
        }


        // 예약 Ref
        val intentCallActivity = Intent(this, CallActivity::class.java)
        myRefReservation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java) == 0){
                    startActivity(intentCallActivity)
                }
                else if(dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java) != null && dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java)!! > 0){
                    text_number.text = dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java).toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        webView = findViewById(R.id.webview)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl("https://store.naver.com/restaurants/detail?id=35832273")
    }

    override fun onResume(){
        super.onResume()
        user = mAuth.currentUser
        if(user != null && user!!.email != null){
            userID = user!!.email!!
        }
        else{
            userID = "No User"
        }
    }

    private fun initScan() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                //the result data is null or empty then/
                Toast.makeText(this, "this data is empty", Toast.LENGTH_SHORT).show()
            } else {
                //this error because result maybe empty so use settext
                afterLoadDataFromQrCode(result.contents.toString())
            }
        } else {
            //the camera will not close if the result is still null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun afterLoadDataFromQrCode(qrResName : String){
        resName = qrResName
        resTime = getTime()

        myRefRestaurant.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild(resName)){
                    waitNum = dataSnapshot.child(resName).child("waitNum").getValue(Int::class.java)!!
                    waitNum += 1
                    myRefRestaurant.child(resName).child("waitNum").setValue(waitNum)
                }
                else{
                    Log.d("zxcv","Error? : dataSnapshot.hasCild(resName) == false")
                }

                var reservationInfo = ReservationInfo(resName, userID, waitNum)
                var userInfo = UserInfo(userID, resName)

                myRefReservation.child(removeAt(userID)).setValue(reservationInfo)
                myRefUser.child(removeAt(userID)).setValue(userInfo)

                show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        myRefRestaurant.child("DataChangeEvent").setValue(0)
        myRefRestaurant.child("DataChangeEvent").removeValue()
    }

    fun show(){
        text_storename.text = resName
        text_number.text = waitNum.toString()
    }

    private fun getTime() : String{
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
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