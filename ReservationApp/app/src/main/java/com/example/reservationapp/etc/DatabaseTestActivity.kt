//package com.example.reservationapp
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import kotlinx.android.synthetic.main.activity_database_test.*
//import kotlinx.android.synthetic.main.activity_main.*
//import java.text.SimpleDateFormat
//import java.util.*
//
//class DatabaseTestActivity : AppCompatActivity() {
//
//    val database = FirebaseDatabase.getInstance()
//    val mAuth = FirebaseAuth.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_database_test)
//
//       /* var myRefExam = database.getReference("UserList")
//        myRefExam.child("20191107143502").getValue(String::class.java);
//        for((index,value) in myRefExam.child) {
//        }
//
//        Toast.makeText(baseContext, "", Toast.LENGTH_SHORT).show()
//*/
//        var resID = "Rest01"
//
//        var resName : String = "Rest Name Default"
//        var userID  : String = "No User Default"
//        var resTime : String = getTime()
//        var waitNum : String = "-1"
//
//        //userInfo는 정보를 담고 toString 한 거
//        var userInfo : String
//        var myRef = database.getReference(resID)
//        var count = myRef.child("count").key?:"-1"
//
//        var user = mAuth.currentUser
//
//        if (user != null) {
//            // Null이 될 수도?
//            userID = user.email!!
//        }
//        else{
//            userID = "No User"
//        }
//        textView_userIDedit.text = userID
//
//        // QR 코드를 찍으면 실행되는 함수라고 가정한다.
//        button_DB_submit.setOnClickListener{
//            if (user != null) {
//                // Null이 될 수도?
//                userID = user.email!!
//            }
//            else{
//                userID = "No User"
//            }
//            resName = editText_resName.text.toString()
//            resTime = getTime()
//
//            myRef = database.getReference(resID)
//
//            userInfo = ReservationInformation(resName, userID, resTime, -1).toString()
//
//            myRef.child("User ID").setValue(userID)
//            myRef.child("Restaurant Name").setValue(resName)
//            myRef.child("Reservation Time").setValue(resTime)
//            myRef.child("Waiting Number").setValue(waitNum)
//            myRef.child("UserInfo" + count.toInt()).setValue(userInfo)
//        }
//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                userInfo = dataSnapshot.child("UserInfo01").getValue(String::class.java)?:"User Info toString"
//
//                myRef = database.getReference(resID)
//
//                textView_userID.text = userID
//                textView_resName.text = resName
//                textView_resTime.text = resTime
//                textView_waitNum.text = userInfo
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("zxcv", "Failed to read value.", error.toException())
//            }
//        })
//
//    }
//
//    private fun getTime() : String{
//        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
//    }
//
//    fun regReservation(){
//
//    }
//
//    companion object {
//        private const val TAG = "DatabaseTestActivity"
//    }
//}