package com.example.remediescontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.remediescontrol.adapter.RemedyAdapter
import com.example.remediescontrol.data.AppDatabase
import com.example.remediescontrol.data.Dao
import com.example.remediescontrol.model.Global
import com.example.remediescontrol.model.Remedy

class MyRemediesActivity : AppCompatActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_remedies)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
            "banco.db").allowMainThreadQueries().build()

        // Recupera todos os Remedios do DAO
        Global.remedies = db.RemedyDao().getAllRemedies()

        // Inicializa a váriavel do botão
        val btnMaintenanceRemedy = findViewById<Button>(R.id.btnMaintenanceRemedy)
        // Atribui a função no evento de Click
        btnMaintenanceRemedy.setOnClickListener { OpenNewRemediesView() }

        // Recupera o recycleView da página
        Global.recyclerView = findViewById<RecyclerView>(R.id.remediesRecycleView)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        Global.recyclerView.setLayoutManager(llm)
        val remedyAdapter = RemedyAdapter(this, Global.remedies)
        Global.recyclerView.adapter = remedyAdapter

        // Insere uma linha divisória entre os itens
        val divider = DividerItemDecoration(Global.recyclerView.context, llm.orientation)
        Global.recyclerView.addItemDecoration(divider)

        // Dispara a função ao clicar no item da lista
        remedyAdapter.onItemClick = { remedy ->
            val intent = Intent(this, MaintenanceRemedyActivity::class.java)
            intent.putExtra("maintenance", true)
            intent.putExtra("id", remedy.id)
            intent.putExtra("name", remedy.name)
            intent.putExtra("category", remedy.category)
            intent.putExtra("quantity", remedy.quantity)
            intent.putExtra("hour", remedy.hour)
            startActivity(intent)
        }
    }

    // Função responsável por chamar a página de inclusão de remádios
    fun OpenNewRemediesView() {
        val intent = Intent(this, MaintenanceRemedyActivity::class.java)
        intent.putExtra("maintenance", false)
        startActivity(intent)
    }
}