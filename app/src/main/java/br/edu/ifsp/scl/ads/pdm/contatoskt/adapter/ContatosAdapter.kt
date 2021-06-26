package br.edu.ifsp.scl.ads.pdm.contatoskt.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.pdm.contatoskt.R
import br.edu.ifsp.scl.ads.pdm.contatoskt.model.Contato

class ContatosAdapter(
    private val contatosList: MutableList<Contato>,
    private val onContatoClickListener: OnContatoClickListener
    ) : RecyclerView.Adapter<ContatosAdapter.ContatoViewHolder>() {

    inner class ContatoViewHolder(viewContato: View): RecyclerView.ViewHolder(viewContato){
        val nomeTv : TextView = viewContato.findViewById(R.id.nomeContatoTv)
        val emailTv : TextView = viewContato.findViewById(R.id.emailContatoTv)
    }

    //Criação de uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val viewContato: View = LayoutInflater.from(parent.context).inflate(R.layout.view_contato, parent, false)
        return ContatoViewHolder(viewContato)
    }

    override fun getItemCount(): Int = contatosList.size


    //Atualiza os valores da view
    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato: Contato = contatosList[position]
        holder.nomeTv.text = contato.nome
        holder.emailTv.text = contato.email
        holder.itemView.setOnClickListener {
            onContatoClickListener.onContatoClick(position)
        }
    }


}