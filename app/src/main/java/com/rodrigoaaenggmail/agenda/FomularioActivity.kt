package com.rodrigoaaenggmail.agenda

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_fomulario.*

class FomularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fomulario)
        setSupportActionBar(toolbar)

        val ButtonSave = findViewById<Button>(R.id.formulario_btSalvar)
        ButtonSave.setOnClickListener {view -> val intent =
            Toast.makeText (this@FomularioActivity, "Botao clicado", Toast.LENGTH_SHORT).show()
        }


    }

}
