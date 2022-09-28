package com.example.vetterappplus.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vetterappplus.R
import com.example.vetterappplus.models.RegistrationOb
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ResetPasword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pasword)


        val returnLogin = findViewById<ImageView>(R.id.returnLogin)
        val buttonResetPassword = findViewById<com.google.android.material.button.MaterialButton>(R.id.resetPassword)

        returnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonResetPassword.setOnClickListener{
            validate()
        }
    }

    fun validate(){
        val campoDocumento = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tvDocumento)
        val tvDocumento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.documento).text

        if(tvDocumento.toString().isEmpty()){
            campoDocumento.setError("Este campo es obligatorio")
        }else{
            form(condition = false, progress = true)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    resetCredential()
                }
            }, 1500)
        }
    }

    fun form(condition: Boolean, progress: Boolean){
        val campoDocumento = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tvDocumento)
        val buttonResetPassword = findViewById<com.google.android.material.button.MaterialButton>(R.id.resetPassword)
        val progres = findViewById<ProgressBar>(R.id.progressBar)

        progres.isVisible = progress
        campoDocumento.isVisible = condition
        buttonResetPassword.isVisible = condition
    }

    fun resetCredential(){

        val tvDocumento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.documento).text
        val url = "https://streamit.com.co/VetterApp/recuperacion_credenciales.php"
        val queue = Volley.newRequestQueue(this@ResetPasword)

        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, object : Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    try {
                        val jsonObject = JSONObject(response)

                        val result =
                            "User Name : " + jsonObject.getString("name") + "\n" + "Job : " + jsonObject.getString(
                                "job"
                            ) + "\n" + "Updated At : " + jsonObject.getString("updatedAt")


                    } catch (e: JSONException) {
                        e.printStackTrace()

                        val obj = JSONObject(response)

                        if (obj.getString("message") == "Consulta exitoso"){
                            AlertDialog.Builder(this@ResetPasword).apply {
                                setTitle("Informacion Consultada")
                                setMessage("Se a enviado un correo electronico con la informacion a " + obj.getString("correo"))
                                setPositiveButton("Continuar"){
                                        _: DialogInterface, _: Int ->
                                    startActivity(Intent(this@ResetPasword, MainActivity::class.java))
                                }
                            }.show()
                            form(condition = true, progress = false)
                        }else if(obj.getString("message") == "El documento consultado no se encuentra en el sistema"){
                            AlertDialog.Builder(this@ResetPasword).apply {
                                setTitle("Informacion Consultada")
                                setMessage(obj.getString("message"))
                                setNegativeButton("Cerrar", null)
                            }.show()
                            form(condition = true, progress = false)
                        }
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "error is " + error!!.message)
                    Toast.makeText(this@ResetPasword, "Fail to update data..", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["cedula"] = tvDocumento.toString()
                    return params
                }
            }
        queue.add(request)
    }


}