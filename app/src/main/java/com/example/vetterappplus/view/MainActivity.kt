package com.example.vetterappplus.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vetterappplus.R
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val resetPasword = findViewById<Button>(R.id.resetPassword)
        val uiRegister = findViewById<Button>(R.id.register)
        val login = findViewById<com.google.android.material.button.MaterialButton>(R.id.login)

        resetPasword.setOnClickListener {
            startActivity(Intent(this, ResetPasword::class.java))
        }

        uiRegister.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }

        login.setOnClickListener{
            validate()
        }
    }


    fun validate(){

        val conUser = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputName)
        val tvUser = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.user).text
        val conPasswrod = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.conPasword)
        val tvPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.pasword).text

        if(tvUser.toString().isEmpty()){
            conUser.setError("Este campo es obligatorio")
        }else if (tvPassword.toString().isEmpty()){
            conPasswrod.setError("Este campo es obligatorio")
        }else{
            form(condition = false, progress = true)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    login()
                }
            }, 1500)
        }
    }

    fun form(condition: Boolean, progress: Boolean){

        val login = findViewById<com.google.android.material.button.MaterialButton>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val progres = findViewById<ProgressBar>(R.id.progressBar)

        progres.isVisible = progress
        login.isVisible = condition
        register.isVisible = condition

    }


    fun login(){

        val tvUser = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.user).text
        val tvPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.pasword).text
        val url = "https://streamit.com.co/VetterApp/login.php"
        val queue = Volley.newRequestQueue(this@MainActivity)

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

                        if (obj.getString("message") == "Bienvenido"){
                            Toast.makeText(this@MainActivity, obj.getString("message"), Toast.LENGTH_SHORT).show()
                            form(condition = true, progress = false)
                        }else if(obj.getString("message") == "Usuario y/o password incorrectos"){
                            AlertDialog.Builder(this@MainActivity).apply {
                                setTitle("Informacion!!!")
                                setMessage("Usuarop y/o constraseña incorrectos!")
                                setNegativeButton("Cerrar", null)
                            }.show()
                            form(condition = true, progress = false)
                        }
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "error is " + error!!.message)
                    Toast.makeText(this@MainActivity, "Fail to update data..", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["usuario"] = tvUser.toString()
                    params["password"] = tvPassword.toString()
                    return params
                }
            }
        queue.add(request)
    }

}