package com.example.reservationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_call_confirm_acitivity.*
import kotlinx.android.synthetic.main.list_menu.*
import kotlinx.android.synthetic.main.list_menu.text_foodname
import kotlinx.android.synthetic.main.list_menu.text_price
import kotlinx.android.synthetic.main.list_option.*

class CallConfirmAcitivity : AppCompatActivity() {


    val menuarray by lazy { intent.extras?.get("Menu") as Calllist}
    val listarray by lazy{intent.extras?.get("list") as ArrayList<OptionList>}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_confirm_acitivity)
        text_foodname.text = menuarray.foodname
        text_price.text = menuarray.price
        val optionlist : ListView= findViewById(R.id.calllist_option)
        val adapter = OptionAdapter(this, listarray)
        optionlist.adapter = adapter

        bt_주문함.setOnClickListener{
            //바로 firebase에다가 주문한게 들어가기
            Toast.makeText(this, "주문함에 담겼습니다.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
            finish()
        }
        bt_pay.setOnClickListener{
            //결제 화면으로 넘어가기
            val intent = Intent(this,PaymentActivity::class.java)

            startActivity(intent)

        }
    }
}
