package com.rodrigoaaenggmail.agenda

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
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
import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Browser
import android.support.v4.app.ActivityCompat
import android.view.Menu
import java.security.Permission
import java.util.*
import java.util.jar.Manifest


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

        /* Cor da Actionbar */
        val bar = supportActionBar
        bar!!.setBackgroundDrawable(ColorDrawable(parseColor("#440144")))



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

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val dao = AlunoDAO (this@ListaAlunosActivit)
        val aluno = listaAlunos!!.getItemAtPosition(info.position) as Aluno
        var nomeSite = aluno.site

        val itemLigar = menu!!.add ("Ligar")
        itemLigar.setOnMenuItemClickListener (object: MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (ActivityCompat.checkSelfPermission(this@ListaAlunosActivit, android.Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@ListaAlunosActivit,
                                                      arrayOf (android.Manifest.permission.CALL_PHONE),12)
                } else {
                    val intentLigar = Intent(Intent.ACTION_CALL)
                    intentLigar.setData(Uri.parse("tel:" + aluno.telefone))
                    startActivity(intentLigar)
                }

                return false
            }
        })



        val itemSMS = menu.add ("Envia SMS")
        val intentSMS = Intent (Intent.ACTION_VIEW)
        intentSMS.setData(Uri.parse("sms:" + aluno.telefone))
        itemSMS.setIntent(intentSMS)

        val itemEndereco = menu.add ("Visualizar no mapa")
        val intentMapa = Intent (Intent.ACTION_VIEW)
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.endereco))
        itemEndereco.setIntent(intentMapa)


        if (!nomeSite!!.startsWith("http://")){
            nomeSite = "http://" + aluno.site
        }

        val site = menu.add ("Visitar site")
        val intentSite = Intent (Intent.ACTION_VIEW)
        intentSite.setData(Uri.parse(nomeSite))
        site.setIntent(intentSite)


        val deletar  = menu.add("Deletar")
            deletar.setOnMenuItemClickListener (object: MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {


                    dao.deleta (aluno)
                    carregaLista()
                    Toast.makeText(this@ListaAlunosActivit, "Deletar o aluno " + aluno.nome, Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        )
    }

    // Ver o resultado da permissão logo apos a solicitação da permissão.
    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 12) {
            // faz a ligação
        }
    } */

}
