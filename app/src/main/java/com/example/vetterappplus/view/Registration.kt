package com.example.vetterappplus.view

import android.app.ProgressDialog
import android.content.ContextParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
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

            loadArtists()
            //guardarDatos()

        }






        //Get response

    }

    fun guardarDatos(){

        var nombre = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.nombre).text
        var apellido = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.apellido).text
        var documento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.idDocumento).text
        var correo = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.correo).text

        var url = "https://streamit.com.co/VetterApp/registro_usuario.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String>{ response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                }catch (e : JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() } ) {
            @Throws(AuthFailureError::class)

            override fun getParams() : Map<String, String>{
                val params = HashMap<String, String>()
                params.put("nombre", nombre.toString())
                params.put("apellido", apellido.toString())
                params.put("documento", documento.toString())
                params.put("correo", correo.toString())
                return params
            }

        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)

    }

    private fun loadArtists() {
        var registroUsuario: MutableList<RegistrationOb>? = null

        var nombre = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.nombre).text
        var apellido = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.apellido).text
        var documento = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.idDocumento).text
        var correo = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.correo).text

        val url = "https://streamit.com.co/VetterApp/registro_usuario.php"
        val queue = Volley.newRequestQueue(this@Registration)


        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, object : Response.Listener<String?> {
                override fun onResponse(response: String?) {

                    // on below line we are displaying a toast message as data updated.
                    Toast.makeText(this@Registration, "Cargando", Toast.LENGTH_SHORT).show()
                    try {
                        // on below line we are extracting data from our json object
                        // and passing our response to our json object.
                        val jsonObject = JSONObject(response)

                        // creating a string for our output.
                        val result =
                            "User Name : " + jsonObject.getString("name") + "\n" + "Job : " + jsonObject.getString(
                                "job"
                            ) + "\n" + "Updated At : " + jsonObject.getString("updatedAt")

                        Log.e("Aqui!!!! -> ", "llego")
                        Log.e("Mensaje!!!! -> ", result)

                        Toast.makeText(this@Registration, "Mensaje2 -> " + response, Toast.LENGTH_SHORT).show()

                    } catch (e: JSONException) {
                        e.printStackTrace()

                        val obj = JSONObject(response)

                        Toast.makeText(this@Registration, obj.getString("message"), Toast.LENGTH_LONG).show()
                        Log.e("Mensaje2!!!! -> ", "catch E-> " + obj.getString("message"))
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

                    // below line we are creating a map for storing
                    // our values in key and value pair.
                    val params: MutableMap<String, String> = HashMap()

                    // on below line we are passing our key
                    // and value pair to our parameters.
                    params["nombre"] = nombre.toString()
                    params["apellido"] = apellido.toString()
                    params["documento"] = documento.toString()
                    params["correo"] = correo.toString()

                    // at last we are
                    // returning our params.
                    return params
                }
            }
        // below line is to make
        // a json object request.
        queue.add(request)


    }
}

