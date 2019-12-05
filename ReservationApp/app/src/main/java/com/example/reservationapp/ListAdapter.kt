package com.example.reservationapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class ListAdapter(val context :Context, val calllist:ArrayList<Calllist>) :BaseAdapter(){
    override fun getCount(): Int {
        return calllist.size
    }

    override fun getItem(idx: Int): Any {
3
            return calllist[idx]

    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View? {




        val view = LayoutInflater.from(context).inflate(R.layout.list_menu, parent,false) as View

        view.findViewById<TextView>(R.id.text_foodname).text = calllist[idx].foodname
        view.findViewById<TextView>(R.id.text_price).text = calllist[idx].price
        val resourceId = context.resources.getIdentifier(calllist[idx].foodphoto,"drawable",context.packageName)
        view.findViewById<ImageView>(R.id.image_view).setImageResource(resourceId)
        //resourceId로 해가지고 foodphoto에서 가지고온 string과 똑같은 drawble에 있는 사진을 대입 시키기
            return view
    }
}