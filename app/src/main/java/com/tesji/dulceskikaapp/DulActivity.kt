package com.tesji.dulceskikaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DulActivity : AppCompatActivity(), Adapter.OnItemClickListener {

    private val db = FirebaseFirestore.getInstance()
    private val coleccion = db.collection("Dulces")
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dul)

        recyclerView = findViewById(R.id.rDatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(this)
        recyclerView.adapter = adapter

        val btnConsulta : Button = findViewById(R.id.btnRecargar)
        val btnInser : Button = findViewById(R.id.btnInsertar)
        val btnUpdate : Button = findViewById(R.id.btnActualizar)
        val btnDelete : Button = findViewById(R.id.btnEliminar)
        val btnCerrarSesion :Button = findViewById(R.id.btnClose)

        btnCerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        btnDelete.setOnClickListener {
            val txtId : TextView = findViewById(R.id.txt_ID)
            var id1 : String = txtId.text.toString()
            coleccion.document(id1)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Se elimino correctamente", Toast.LENGTH_SHORT).show()
                    consultarColecccion()
                }
                .addOnFailureListener {e->
                    Toast.makeText(this, "Ocurrio un error" + e.message, Toast.LENGTH_SHORT).show()
                    consultarColecccion()
                }
        }

        btnUpdate.setOnClickListener {
            val txtNom : TextView = findViewById(R.id.txt_Nombre)
            val txtPrecio : TextView = findViewById(R.id.txt_Precio)
            val txtId : TextView = findViewById(R.id.txt_ID)

            if(txtNom.text.toString().trim().isEmpty()){
                txtNom.setError("Ingrese un nombre")
            }else if(txtPrecio.text.toString().trim().isEmpty()){
                txtPrecio.setError("Ingrese un precio")
            }else{
                var nom : String = txtNom.text.toString()
                var pre : Int = Integer.parseInt(txtPrecio.text.toString())
                var id1 : String = txtId.text.toString()

                val docActualizado = HashMap<String, Any>()
                docActualizado["Nombre"] = nom
                docActualizado["Precio"] = pre
                coleccion.document(id1)
                    .update(docActualizado)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Actualizacion Exitosa", Toast.LENGTH_SHORT).show()
                        consultarColecccion()
                    }
                    .addOnFailureListener { e ->
                    }
            }




        }

        btnConsulta.setOnClickListener(){
            consultarColecccion()
        }

        btnInser.setOnClickListener(){
            val db = FirebaseFirestore.getInstance()
            val txtNom : TextView = findViewById(R.id.txt_Nombre)
            val txtPrecio : TextView = findViewById(R.id.txt_Precio)
            if(txtNom.text.toString().trim().isEmpty()){
                txtNom.setError("Ingrese un nombre")
            }else if(txtPrecio.text.toString().trim().isEmpty()){
                txtPrecio.setError("Ingrese un precio")
            }else{
                val nom : String = txtNom.text.toString()
                val pre : Int = Integer.parseInt(txtPrecio.text.toString())
                val data = hashMapOf(
                    "Nombre" to nom,
                    "Precio" to pre)
                db.collection("Dulces")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        consultarColecccion()
                    }
                    .addOnSuccessListener { e ->
                    }
            }



        }
    }

    private fun consultarColecccion(){
        coleccion.get().addOnSuccessListener { querySnapshot ->
            val listaTuModelo = mutableListOf<Dulces>()
            for (document in querySnapshot){
                val nombre = document.getString("Nombre")
                val precio = document.getLong("Precio")?.toInt()
                val ID = document.id
                if(nombre != null && precio != null){
                    val tuModelo = Dulces(ID, nombre, precio)
                    listaTuModelo.add(tuModelo)
                }
            }
            adapter.setDatos(listaTuModelo)
        }
    }

    override fun onItemClick(tuModelo: Dulces) {
        val txtNom : TextView = findViewById(R.id.txt_Nombre)
        val txtPrecio : TextView = findViewById(R.id.txt_Precio)
        val txtId : TextView = findViewById(R.id.txt_ID)

        txtNom.text = tuModelo.nombre
        txtPrecio.text = tuModelo.precio.toString()
        txtId.text = tuModelo.id
    }
}