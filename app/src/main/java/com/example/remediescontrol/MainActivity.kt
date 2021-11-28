package com.example.remediescontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa a váriavel do botão
        val btnOpenMyRemedies = findViewById<Button>(R.id.btnOpenMyRemedies)
        // Atribui a função no evento de Click
        btnOpenMyRemedies.setOnClickListener { OpenMyRemediesView() }
    }

    // Função responsável por chamar a página de listagem de remédios
    fun OpenMyRemediesView() {
        val intent = Intent(this, MyRemediesActivity::class.java)
        startActivity(intent)
    }
}