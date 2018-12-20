package com.rodrigoaaenggmail.agenda

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        setSupportActionBar(toolbar)

        val alunos = arrayOf("Rodrigo", "Thiago", "Maria")
        val listaAlunos = findViewById<ListView>(R.id.lista_alunos)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos)

        listaAlunos.adapter = adapter

        val novoAluno = findViewById <Button>(R.id.novoAluno)
        novoAluno.setOnClickListener {
            val intentVaiProFormulario = Intent(this@ListaAlunosActivit, FomularioActivity::class.java)
            startActivity (intentVaiProFormulario)
        }
    }


}
