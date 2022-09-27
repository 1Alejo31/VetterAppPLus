package com.example.vetterappplus.models

import androidx.databinding.BaseObservable
import java.util.*

class RegistrationObservable : BaseObservable(){

    private val registerRepository = RegisterRepository()

    //Conexion Directa con el repositorio
    fun registerUsers(){
        registerRepository.getData()
    }


    //ViewModel

}