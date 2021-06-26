package br.edu.ifsp.scl.ads.pdm.contatoskt.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityContatoBinding
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Contato

class ContatoActivity : AppCompatActivity() {

    private lateinit var activityContatoBinding: ActivityContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityContatoBinding = ActivityContatoBinding.inflate(layoutInflater)
        setContentView(activityContatoBinding.root)
    }

    fun onClickButton(view: View) {
        val contato: Contato
        with(activityContatoBinding) {
            contato = Contato(
                nomeCompletoEt.text.toString(),
                emailEt.text.toString(),
                telefoneEt.text.toString(),
                comercialSw.isChecked,
                celularEt.text.toString(),
                sitePessoalEt.text.toString()
            )
        }

        if (view == activityContatoBinding.salvarBt) {
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, contato)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }
}