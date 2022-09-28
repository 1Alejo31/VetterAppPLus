package com.example.vetterappplus.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContextParams
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vetterappplus.R
import com.example.vetterappplus.models.RegistrationOb
import com.example.vetterappplus.models.VolleySingleton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.Throws

class Registration : AppCompatActivity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val returnLoginR = findViewById<ImageView>(R.id.returnLoginR)

        returnLoginR.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        //Set registration

        var registrarInfo = findViewById<com.google.android.material.button.MaterialButton>(R.id.registrar)


        registrarInfo.setOnClickListener{
            validate()
        }
        //Get response

    }


    private fun guardarDatos() {
        var registroUsuario: MutableList<RegistrationOb>? = null
        val builder = AlertDialog.Builder(this)
        var nombre = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.nombre).text
        var apellido = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.apellido).text
        var documento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.idDocumento).text
        var correo = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.correo).text


        val url = "https://streamit.com.co/VetterApp/registro_usuario.php"
        val queue = Volley.newRequestQueue(this@Registration)


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

                        if (obj.getString("message") == "El usuario ya se encuentra registrado"){
                            AlertDialog.Builder(this@Registration).apply {
                                setTitle("El documento ya se encuentra registrado")
                                setMessage("lo sentimos, el documento ${documento}," +
                                        " ya se encciemtra registrado")
                                setNegativeButton("Cerrar", null)
                            }.show()
                            form(condition = true, progress = false)
                        }else if(obj.getString("message") == "Disculpa, el correo ya estas registrado en la App"){
                            AlertDialog.Builder(this@Registration).apply {
                                setTitle("El correo ya se encuentra registrado")
                                setMessage("Lo sentimos, el correo ingresado ${correo} " +
                                        " ya se encuentra registrado")
                                setNegativeButton("Cerrar", null)
                            }.show()
                            form(condition = true, progress = false)
                        }else if(obj.getString("message") == "Registro exitoso"){
                            AlertDialog.Builder(this@Registration).apply {
                                setTitle("Registro Exitoso!")
                                setMessage("Bienvenido a Vetter app, un lugar para cuidar de tu mascota")
                                setPositiveButton("Continuar"){
                                    _: DialogInterface, _: Int ->
                                    startActivity(Intent(this@Registration, MainActivity::class.java))
                                }
                            }.show()
                            form(condition = true, progress = false)
                        }
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    // displaying toast message on response failure.
                    Log.e("tag", "error is " + error!!.message)
                    Toast.makeText(this@Registration, "Fail to update data..", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()

                    params["nombre"] = nombre.toString()
                    params["apellido"] = apellido.toString()
                    params["documento"] = documento.toString()
                    params["correo"] = correo.toString()

                    return params
                }
            }
        queue.add(request)
    }

    fun form(condition: Boolean, progress: Boolean){
        val tvNombre = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputNombre)
        val tvApellido = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputApellido)
        val tvDocumento = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.documento)
        val tvCorreo = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputCorreo)
        val tvGuardar = findViewById<com.google.android.material.button.MaterialButton>(R.id.registrar)
        val progres = findViewById<ProgressBar>(R.id.progressBar)

            progres.isVisible = progress

            tvNombre.isVisible = condition
            tvApellido.isVisible = condition
            tvDocumento.isVisible = condition
            tvCorreo.isVisible = condition
            tvGuardar.isVisible = condition

    }

    fun validate(){

        var nombre = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.nombre).text
        var apellido = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.apellido).text
        var documento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.idDocumento).text
        var correo = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.correo).text

        val tvNombre = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputNombre)
        val tvApellido = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputApellido)
        val tvDocumento = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.documento)
        val tvCorreo = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textInputCorreo)


        if (nombre.toString().isEmpty()){
            tvNombre.setError("Este campo es obligatorio")
        }else if(apellido.toString().isEmpty()){
            tvApellido.setError("Este campo es obligatorio")
        }else if(documento.toString().isEmpty()){
            tvDocumento.setError("Este campo es obligatorio")
        }else if(correo.toString().isEmpty()){
            tvCorreo.setError("Este campo es obligatorio")
        }else{
            form(condition = false, progress = true)

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    guardarDatos()
                }
            }, 1500)
        }
    }
}

