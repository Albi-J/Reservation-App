package com.example.reservationapp

import android.content.Context
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class OptionAdapter(val context : Context, val OptionList :ArrayList<OptionList>) : BaseAdapter(){
    override fun getCount(): Int {
        return OptionList.size
    }

    override fun getItem(idx: Int): Any {

        return OptionList[idx]

    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View? {


        val view = LayoutInflater.from(context).inflate(R.layout.list_option,parent,false) as View
        view.findViewById<TextView>(R.id.text_option).text = OptionList[idx].optionname
        view.findViewById<TextView>(R.id.text_callnumber).text =OptionList[idx].Callnumber.toString()

        val plus = view.findViewById<ImageButton>(R.id.bt_plus)
        plus.setOnClickListener {
            OptionList[idx].Callnumber += 1
            view.findViewById<TextView>(R.id.text_callnumber).text =OptionList[idx].Callnumber.toString()
            //이게 중복되어 있다고 해서
        }
        val minus = view.findViewById<ImageButton>(R.id.bt_minus)
        minus.setOnClickListener {
            if(OptionList[idx].Callnumber >0) {
                OptionList[idx].Callnumber -= 1
            }
            view.findViewById<TextView>(R.id.text_callnumber).text =OptionList[idx].Callnumber.toString()
        }

        return view
    }
}