package com.example.reservationapp.DbStructure

class MenuInfo (resName : String, menuName : String){
    var resName : String = resName
    var menuName : String = menuName
    var menuPrice : Int? = 0

    constructor(resName : String, menuName : String, menuPrice : Int) : this(resName, menuName){
        this.resName = resName
        this.menuName = menuName
        this.menuPrice = menuPrice
    }
}