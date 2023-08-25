package com.example.electroapp.presenation.fragments

import androidx.fragment.app.Fragment
import com.example.electroapp.data.models.User
import com.example.electroapp.presenation.listeners.AdListenerImpl

abstract class ElectroFragment: Fragment() {
    protected var listener: AdListenerImpl?=null
    //protected var user: User?= null

    fun setUser(newUser: User){
       // this.user = newUser

    }

}