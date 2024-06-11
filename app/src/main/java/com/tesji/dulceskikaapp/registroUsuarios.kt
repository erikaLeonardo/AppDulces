package com.tesji.dulceskikaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class registroUsuarios : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuarios)
        val txt_nombre_nuevo : TextView = findViewById(R.id.txtNombre_Usuario)
        val txtCorreoNuevo : TextView = findViewById(R.id.txt_correo_electronico)
        val txtPassword : TextView = findViewById(R.id.txt_password)
        val txtNuevoPassword : TextView = findViewById(R.id.txt_confirmar_password)
        val btn_crear : Button = findViewById(R.id.btn_crear_cuenta)

        btn_crear.setOnClickListener(){
            var psw1 = txtPassword.text.toString()
            var psw2 = txtNuevoPassword.text.toString()

            if(psw1.equals(psw2)){
                createAccount(txtCorreoNuevo.text.toString(), txtPassword.text.toString())

            }else{
                Toast.makeText(baseContext, "Error: Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                txtPassword.requestFocus()
            }
        }
        firebaseAuth = Firebase.auth
    }
    private fun createAccount(email:String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if(task.isSuccessful){
                sendEmailVerification()
                Toast.makeText(baseContext, "Verifique su correo para concluir su registro", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(baseContext, "Algo salio mal, ERROR:"
                        + task.exception, Toast.LENGTH_SHORT)
            }
        }
    }
    private fun sendEmailVerification(){

        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){
                task->
            if(task.isSuccessful){
                Toast.makeText(baseContext, "Verifica tu correo en tu cuenta para continuar", Toast.LENGTH_SHORT).show()
            }else{}
        }
    }
}