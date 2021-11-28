package com.example.remediescontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.remediescontrol.data.AppDatabase
import com.example.remediescontrol.model.Global
import com.example.remediescontrol.model.Remedy

class MaintenanceRemedyActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var selectedCategory : String
    lateinit var spinnerCategory : Spinner
    lateinit var seekBarQuantity : SeekBar
    lateinit var edtName : EditText
    lateinit var edtHour : EditText
    lateinit var txtQuantityValue : TextView
    lateinit var btnDeleteRemedy : Button
    var maintenance : Boolean = false
    var remedyId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_remedy)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
            "banco.db").allowMainThreadQueries().build()

        // Recupera os edits, textViews, seekBar e spinner
        seekBarQuantity = findViewById(R.id.seekBarQuantity)
        edtName = findViewById(R.id.edtName)
        edtHour = findViewById(R.id.edtHour)
        txtQuantityValue = findViewById(R.id.txtQuantityValue)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        btnDeleteRemedy = findViewById(R.id.btnDeleteRemedy)

        // Tratativa Spinner
        // Inicializa ao array de Categorias do Spinner
        var categories = arrayOf(
            "Pressão arterial",
            "Relaxante muscular",
            "Antibiótico",
            "Anti-inflamatório"
        )

        // Inicializa o adapter com as categorias declaradas acima
        spinnerCategory.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, categories)
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = categories.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        // Inicializa a váriavel do botão
        val btnSaveRemedy = findViewById<Button>(R.id.btnSaveRemedy)
        // Atribui a função no evento de Click
        btnSaveRemedy.setOnClickListener { saveRemedy() }

        // Inicializa eventos onChange no seekBar
        seekBarQuantity.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                // Quando houver alterção de valor
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // Atualiza o txt de quantidade
                    txtQuantityValue.text = seekBar?.progress.toString()
                }

                // Função responsavel por disparar evento quando clica sobre o seekBar
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                // Função responsavel por disparar evento quando
                // para de pressionar o seekBar
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        // Recupera parametros passado pelo intent
        maintenance = intent.getBooleanExtra("maintenance", false)
        // Verefica se é uma manutenção
        if (maintenance) {

            // Recupera o Id do remedio
            remedyId = intent.getIntExtra("id", 0)

            // Atualiza Edits e SeekBar
            edtName.setText(intent.getStringExtra("name"))
            edtHour.setText(intent.getStringExtra("hour"))
            seekBarQuantity.progress = intent.getIntExtra("quantity", 0)
            val categoryRemedy = intent.getStringExtra("category")

            // Inicializa a váriavel que salvara o index da categoria
            var indexCategory : Int = 0
            // Encontra o index da categoria do Remédio
            var index = 0
            categories.forEach { category ->
                if (category.equals(categoryRemedy)) {
                    indexCategory = index
                }
                index++
            }

            // Atualiza seleção do Spinner
            spinnerCategory.setSelection(indexCategory)

            // Atualiza a tela
            // Exibe o botão
            btnDeleteRemedy.visibility = View.VISIBLE
            btnDeleteRemedy.setOnClickListener { deleteRemedy() }

            // Atualiza a função do botão salvar
            btnSaveRemedy.setOnClickListener { updateRemedy() }
        }
    }

    fun saveRemedy() {

        // Cria o novo objeto de Remedio
        val newRemedy = Remedy(
            0,
            edtName.text.toString(),
            selectedCategory,
            txtQuantityValue.text.toString().toInt(),
            edtHour.text.toString()
        )

        // Inclui o novo remedio no BD
        db.RemedyDao().newRemedy(newRemedy)

        newRemedy.id = db.RemedyDao().getId()

        // Adiciona o novo Remedio ao List
        Global.remedies.add(newRemedy)

        // Atualiza recycle view com o novo remedio
        Global.recyclerView.adapter?.notifyDataSetChanged()

        // Exibe o toast de sucesso
        Toast.makeText(this, "Remédio ${edtName.text.toString()} salvo com sucesso!", Toast.LENGTH_LONG).show()

        // Chama a função que limpa dos dados da View
        updateView()
    }

    fun deleteRemedy() {
        db.RemedyDao().deleteRemedy(remedyId)

        // Tratativa para encontrar o index do remedio
        var count = 0
        var indexRemedy = 0
        // Encontra o index do remedio pelo Id
        Global.remedies.forEach { remedy ->
            if (remedy.id == remedyId) {
                indexRemedy = count
            }
            count++
        }

        // Remove o remedio do List
        Global.remedies.removeAt(indexRemedy)

        // Notifica o recycler da remoção do Remedio
        Global.recyclerView.adapter?.notifyItemRemoved(indexRemedy)

        // Exibe o toast de sucesso
        Toast.makeText(this, "Remédio ${edtName.text.toString()} excluido com sucesso!", Toast.LENGTH_LONG).show()

        // Chama a função que reseta a view
        updateView()
    }

    fun updateRemedy() {
        var updatedRemedy = Remedy (
            remedyId,
            edtName.text.toString(),
            selectedCategory,
            txtQuantityValue.text.toString().toInt(),
            edtHour.text.toString()
        )

        db.RemedyDao().updateRemedy(updatedRemedy)

        // Tratativa para encontrar o index do remedio
        var count = 0
        var indexRemedy = 0
        // Encontra o index do remedio pelo Id
        Global.remedies.forEach { remedy ->
            if (remedy.id == remedyId) {
                indexRemedy = count
            }
            count++
        }

        Global.remedies.set(indexRemedy, updatedRemedy)

        // Notifica o recycler da alteração do Remedio
        Global.recyclerView.adapter?.notifyItemChanged(indexRemedy)

        // Exibe o toast de sucesso
        Toast.makeText(this, "Remédio ${edtName.text.toString()} atualizado com sucesso!", Toast.LENGTH_LONG).show()

    }

    fun updateView() {
        maintenance = false
        // Limpa os dados da tela
        edtName.text.clear()
        edtHour.text.clear()
        txtQuantityValue.text = "0"
        seekBarQuantity.progress = 0
        spinnerCategory.setSelection(0)
        btnDeleteRemedy.visibility = View.GONE
    }
}