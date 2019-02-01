package com.rodrigoaaenggmail.agenda.modelo
import android.graphics.Bitmap
import java.io.Serializable

class Aluno : Serializable {

    var id: Long? = null
    var nome: String? = null
    var endereco: String? = null
    var telefone: String? = null
    var site: String? = null
    var comentario: String? = null
    var nota: Double? = null
    var CaminhoFoto: String? = null

    override fun toString(): String {
        return id.toString() + " - " + nome
    }
}