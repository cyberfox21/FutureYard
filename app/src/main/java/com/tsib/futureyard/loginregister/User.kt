package com.tsib.futureyard.loginregister

class User {
    var fio: String = null.toString()
    var email: String = null.toString()
    var password: String = null.toString()
    var adress: String = null.toString()
    var uid: String = null.toString()


    constructor(fio: String, email: String, password: String, adress: String, uid: String) {
        this.fio = fio
        this.email = email
        this.password = password
        this.adress = adress
        this.uid = uid
    }

    constructor()
}