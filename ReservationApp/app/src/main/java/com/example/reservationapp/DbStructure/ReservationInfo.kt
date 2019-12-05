package com.example.reservationapp.DbStructure

class ReservationInfo (resName : String, userID : String){
    var resName : String = resName
    var userID : String = userID
    var resTime : String? = ""
    var waitNum : Int? = -1

    constructor(resName : String, userID : String, resTime : String) : this(resName, userID){
        this.resName = resName
        this.userID = userID
        this.resTime = resTime
    }

    constructor(resName : String, userID : String, waitNum : Int) : this(resName, userID){
        this.resName = resName
        this.userID = userID
        this.waitNum = waitNum
    }

    constructor(resName : String, userID : String, resTime : String, waitNum : Int) : this(resName, userID){
        this.resName = resName
        this.userID = userID
        this.resTime = resTime
        this.waitNum = waitNum
    }

    constructor(resName : String, userID : String, waitNum : Int, resTime : String) : this(resName, userID){
        this.resName = resName
        this.userID = userID
        this.resTime = resTime
        this.waitNum = waitNum
    }
}