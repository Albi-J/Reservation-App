package com.example.reservationapp.DbStructure

class ResInfo (resName : String){
    var resName : String = resName
    var waitNum : Int = 0

    constructor(resName : String, waitNum : Int) : this(resName){
        this.resName = resName
    }
}