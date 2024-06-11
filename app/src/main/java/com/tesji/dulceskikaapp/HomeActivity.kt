package com.tesji.dulceskikaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val irDulces = findViewById<Button>(R.id.btnDulces)
        irDulces.setOnClickListener{
            val intent = Intent(this, DulActivity::class.java)
            startActivity(intent)
        }

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider:String){

        val txtEmail2 = findViewById<TextView>(R.id.emailtextView)
        val txtProvider = findViewById<TextView>(R.id.providertextView)
        val btnSesionClose = findViewById<Button>(R.id.btnCerrarSesion)

        title = "Inicio"
        txtEmail2.text = email
        txtProvider.text = provider

        btnSesionClose.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

}