package com.example.remediescontrol.model

import android.app.Application
import androidx.recyclerview.widget.RecyclerView

// Classe responsável por definir as váriaveis globais (Utilizada em diversas telas)
class Global: Application() {
    companion object {
        lateinit var remedies: MutableList<Remedy>
        lateinit var recyclerView: RecyclerView
    }
}