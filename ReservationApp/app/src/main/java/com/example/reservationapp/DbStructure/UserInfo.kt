package com.example.reservationapp.DbStructure

class UserInfo (userID : String){
    var userID : String = userID
    var resName : String? = ""

    constructor(userID : String, resName : String) : this(userID){
        this.userID = userID
        this.resName = resName
    }

    fun removeAt() : String{
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