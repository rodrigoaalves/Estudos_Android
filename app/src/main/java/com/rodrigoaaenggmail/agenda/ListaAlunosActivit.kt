package com.rodrigoaaenggmail.agenda

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.rodrigoaaenggmail.agenda.dao.AlunoDAO
import com.rodrigoaaenggmail.agenda.modelo.Aluno

import kotlinx.android.synthetic.main.activity_lista_alunos.*
import java.io.Serializable

class ListaAlunosActivit : AppCompatActivity() {

    var listaAlunos:ListView? = null

/******************************************
    Metodo: onCreate
    Descrição: Metodo utilizado quando a Activiti é criada

 *******************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        setSupportActionBar(toolbar)
        listaAlunos = findViewById<ListView>(R.id.lista_alunos)
        val novoAluno = findViewById <Button>(R.id.novoAluno)
        carregaLista()

        listaAlunos!!.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(lista: AdapterView<*>?, item: View?, position: Int, id: Long) {
                val aluno = listaAlunos!!.getItemAtPosition(position) as (Aluno)
                //Toast.makeText(this@ListaAlunosActivit, "Aluno " + aluno.nome + " clicado!", Toast.LENGTH_SHORT).show()
                val intentVaiproFormulario =  Intent (this@ListaAlunosActivit, FomularioActivity::class.java)
                intentVaiproFormulario.putExtra("aluno", aluno)
                startActivity (intentVaiproFormulario)
            }
        }

        novoAluno.setOnClickListener {
            val intentVaiProFormulario = Intent(this@ListaAlunosActivit, FomularioActivity::class.java)
            startActivity (intentVaiProFormulario)
        }

        registerForContextMenu(listaAlunos)
    }

/******************************************
    Metodo: carregaLista
    Descrição: atualiza lista na Activity

*******************************************/
    fun carregaLista(){
        /* Conecta com banco de dados */
        val dao = AlunoDAO(this)
        // Busca no banco de dados
        // popula o array de strings
        val alunos = dao.buscaAlunos()
        dao.close()
        //val alunos = arrayOf("Rodrigo", "Thiago", "Maria")
        val adapter = ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos)
        listaAlunos!!.adapter = adapter
    }

/******************************************
    Metodo: onResume
    Descrição: Metodo carregado ao virar principal novamente

 *******************************************/
    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    /******************************************
    Metodo: onCreateContextMenu
    Descrição: Cria um menu de contexto

     *******************************************/
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        val deletar  = menu!!.add("Deletar")
            deletar.setOnMenuItemClickListener (object: MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    val info = menuInfo as AdapterView.AdapterContextMenuInfo
                    val dao = AlunoDAO (this@ListaAlunosActivit)
                    val aluno = listaAlunos!!.getItemAtPosition(info.position) as Aluno
                    dao.deleta (aluno)
                    carregaLista()
                    Toast.makeText(this@ListaAlunosActivit, "Deletar o aluno " + aluno.nome, Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        )
    }

}
