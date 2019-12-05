package com.example.reservationapp.DbStructure

class OrderInfo (resName : String, userID : String, menuName : String){
    var resName : String = resName
    var userID : String = userID
    var menuName : String = menuName

    override fun toString(): String {
        return "$resName / $userID / $menuName"
    }
}