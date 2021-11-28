package com.example.remediescontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remediescontrol.R
import com.example.remediescontrol.model.Remedy

class RemedyAdapter (
    private val context: Context,
    private val remedies: MutableList<Remedy>
) : RecyclerView.Adapter<RemedyAdapter.ViewHolder>() {

    var onItemClick: ((Remedy) -> Unit)? = null

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        // Inicializa os campos utilizados no RecycleView
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtCategory: TextView = view.findViewById(R.id.txtCategory)
        val txtQuantity: TextView = view.findViewById(R.id.txtQuantity)
        val txtHour: TextView = view.findViewById(R.id.txtHour)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(remedies[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.linha_recyclerview, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Recupera os dados do Remédio (List) e atualiza o textView
        val remedy = remedies.get(position)
        holder.txtName.text = remedy.name
        holder.txtCategory.text = remedy.category
        holder.txtQuantity.text = remedy.quantity.toString()
        holder.txtHour.text = remedy.hour
    }

    override fun getItemCount(): Int {
        return remedies.size
    }
}