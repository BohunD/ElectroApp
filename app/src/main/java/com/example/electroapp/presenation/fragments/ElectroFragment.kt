package com.example.electroapp.presenation.fragments

import androidx.fragment.app.Fragment
import com.example.electroapp.data.models.User
import com.example.electroapp.presenation.listeners.AdListenerImpl

abstract class ElectroFragment: Fragment() {
    protected var listener: AdListenerImpl?=null
    protected var currentUser: User?= null

    fun setUser(user: User){
        this.currentUser = user
        //listener?.user = user
    }

}