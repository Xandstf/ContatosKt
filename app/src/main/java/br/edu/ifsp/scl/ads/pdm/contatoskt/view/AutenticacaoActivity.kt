package br.edu.ifsp.scl.ads.pdm.contatoskt.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.pdm.contatoskt.AutenticacaoFirebase
import br.edu.ifsp.scl.ads.pdm.contatoskt.databinding.ActivityAutenticacaoBinding

class AutenticacaoActivity : AppCompatActivity() {
    private lateinit var activityAutenticacaoBinding: ActivityAutenticacaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAutenticacaoBinding = ActivityAutenticacaoBinding.inflate(layoutInflater)
        setContentView(activityAutenticacaoBinding.root)
    }

    fun onClick(view: View) {
        when (view) {
            activityAutenticacaoBinding.cadastrarBt -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
            }
            activityAutenticacaoBinding.entrarBt -> {
                val email: String
                val senha: String
                with (activityAutenticacaoBinding) {
                    email = emailEt.text.toString()
                    senha = senhaEt.text.toString()
                }
                AutenticacaoFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
                    Toast.makeText(this, "Usuário autenticado com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Usuário/Senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }
            activityAutenticacaoBinding.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser != null){
            Toast.makeText(this, "Usuário ja autenticado!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}