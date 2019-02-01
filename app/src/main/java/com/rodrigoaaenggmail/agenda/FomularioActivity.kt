package com.rodrigoaaenggmail.agenda

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.FontRequest
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
//import android.widget.EditText foi usado no começo do projeto
import android.widget.Toast
import com.rodrigoaaenggmail.agenda.dao.AlunoDAO
import com.rodrigoaaenggmail.agenda.modelo.Aluno

import kotlinx.android.synthetic.main.activity_fomulario.*
import java.io.File
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.Bitmap
import android.widget.ImageView


class FomularioActivity : AppCompatActivity() {

    var helper: FormularioHelper? = null
    val CameraConst = 321
    val caminhoFoto = "/" + System.currentTimeMillis() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fomulario)
        setSupportActionBar(toolbar)

        /* Cor da Action bar */
        val bar = supportActionBar
        bar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#440144")))

        helper = FormularioHelper(this)

        val intent = getIntent()
        val aluno = intent.getSerializableExtra("aluno") as Aluno?

        if (aluno != null) {
            helper!!.preencheFormulario (aluno)
        }

        val botaoFoto = findViewById<Button>(R.id.formulario_botao_foto)

        botaoFoto.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (ActivityCompat.checkSelfPermission(this@FomularioActivity, android.Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this@FomularioActivity,
                        arrayOf(android.Manifest.permission.CAMERA), 52
                    )
                } else {
                    val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val Path = getExternalFilesDir(null)
                    val arquivoFoto = File(Path, caminhoFoto)
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this@FomularioActivity,
                                        BuildConfig.APPLICATION_ID + ".provider", arquivoFoto))

                    startActivityForResult(intentCamera, CameraConst)
                }
            }
        })


        /*val ButtonSave = findViewById<Button>(R.id.formulario_btSalvar)
        ButtonSave.setOnClickListener {
            Toast.makeText (this@FomularioActivity, "Aluno Salvo", Toast.LENGTH_SHORT).show()
            /*val VaiPraLista = Intent (this@FomularioActivity, ListaAlunosActivit::class.java)
            startActivity(VaiPraLista)**** Muito usado para criar novas activitys */
            finish()
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CameraConst && resultCode == Activity.RESULT_OK) {
            val campoFoto = findViewById<ImageView>(R.id.formulario_foto)
            val Path = getExternalFilesDir(null)
            val arquivoFoto = Path.toString() + caminhoFoto
            val bitmap = BitmapFactory.decodeFile(arquivoFoto)
            if (bitmap != null) {
                val bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
                campoFoto.setImageBitmap(bitmapReduzido)
                campoFoto.setTag(caminhoFoto)
                campoFoto.scaleType = ImageView.ScaleType.FIT_XY
            }
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (resultCode == 321 && resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            bitmapReduzido = bitmap.createScaledBitmap(bitmap, 150, 150, true)
            /*campoFoto.setImageBitmap(bitmapReduzido;
            campoFoto.setTag(caminhoFoto)*/
        }
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_formulario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) when (item.itemId) {
            R.id.menu_formulario_ok -> {
                var aluno = helper!!.pegaAluno()
                // Conecta com o banco
                val dao = AlunoDAO (this)

                if (aluno.id != null) {
                    dao.altera(aluno)
                } else {
                    // faz quary para salvar o aluno
                    dao.insere (aluno)
                }
                // fecha conexão
                dao.close()
                Toast.makeText(this@FomularioActivity, "Aluno " + aluno.nome + " Salvo", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
