package com.tesji.dulceskikaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore


class AuthActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener :FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)
        val btnIngresar : Button = findViewById(R.id.btn_inicio_sesion)
        val txtCorreo : TextView = findViewById(R.id.txt_correo)
        val txtContrasenia : TextView = findViewById(R.id.txt_contrasenia)
        val btnCrear_cuentaNueva : TextView = findViewById(R.id.btn_registro)
        val btnOlvide_contrasenia : TextView = findViewById(R.id.btn_recuperar_contra)

        firebaseAuth = Firebase.auth
        btnIngresar.setOnClickListener(){
            signIn(txtCorreo.text.toString(), txtContrasenia.text.toString())
        }
        btnCrear_cuentaNueva.setOnClickListener(){
            val i = Intent(this, registroUsuarios::class.java)
            startActivity(i)
        }

        btnOlvide_contrasenia.setOnClickListener(){
            val i = Intent(this, recuperarContrasenia::class.java)
            startActivity(i)
        }

    }
    private fun signIn(email:String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->

            if(task.isSuccessful){
                val user = firebaseAuth.currentUser
                val verificar = user?.isEmailVerified
                if(verificar==true) {


                    Toast.makeText(baseContext, "Inicio de sesión exitoso", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(this, DulActivity::class.java)
                    startActivity(i)
                }else{
                    Toast.makeText(baseContext, "Verifica tu cuenta, da click en el correo que se te ha enviado", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(baseContext, "Algo salió mal, intentalo nuevamente", Toast.LENGTH_SHORT).show()

            }
        }

    }


}