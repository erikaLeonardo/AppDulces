package com.tesji.dulceskikaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class recuperarContrasenia : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenia)
        val txtMail : TextView = findViewById(R.id.txt_recuperar_email)
        val btn_enviar : Button = findViewById(R.id.btn_ingresar_correo)

        btn_enviar.setOnClickListener(){
            sendPasswordReset(txtMail.text.toString())
        }
        firebaseAuth = Firebase.auth
    }
    private fun sendPasswordReset(email : String){

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(){
                task->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Correo de cambio de contraseña enviado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(baseContext, "Error, no se pudo concretar el proceso", Toast.LENGTH_SHORT).show()

            }
        }
    }
}