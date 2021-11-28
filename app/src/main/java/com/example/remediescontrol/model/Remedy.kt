package com.example.remediescontrol.model

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Remedy(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var category: String,
    var quantity: Int,
    var hour: String
)