package com.example.vetterappplus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vetterappplus.models.RegistrationObservable

class VetterViewModel : ViewModel(){

    private var registrationObservable = RegistrationObservable()

    fun registrationUser(){
        registrationObservable.registerUsers()
    }

}