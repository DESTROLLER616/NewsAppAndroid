package com.example.newsapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.classes.WebNew
import com.example.classes.adapters.WebNewAdapter
import com.example.classes.examples.WebNewsExamples
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var subtitle: EditText
    private lateinit var bussiness: EditText
    private lateinit var video: EditText
    private lateinit var url: EditText
    private lateinit var date: EditText
    private lateinit var id: EditText

    private lateinit var add: Button
    private lateinit var update: Button
    private lateinit var clear: Button

    private lateinit var search: EditText

    private lateinit var adapter: WebNewAdapter
    private lateinit var binding: ActivityMainBinding

    var listNews = arrayListOf<WebNew>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillRecicleView()
        initComponents()
        setupRecicleView()
    }

    private fun initComponents() {
        title = findViewById(R.id.etTitle)
        subtitle = findViewById(R.id.etSubtitle)
        bussiness = findViewById(R.id.etBussiness)
        video = findViewById(R.id.etVideo)
        url = findViewById(R.id.etLink)
        date = findViewById(R.id.etDate)
        id = findViewById(R.id.etId)
        add = findViewById(R.id.btnAdd)
        update = findViewById(R.id.btnUpdate)
        clear = findViewById(R.id.btnClear)
        search = findViewById(R.id.svSearch)

        add.setOnClickListener {
            addElement()
        }

        update.setOnClickListener {
            updateElement()
        }

        clear.setOnClickListener {
            clearTexts()
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterResults(s.toString())
            }
        })
    }

    private fun fillRecicleView() {
        listNews = WebNewsExamples.webNewsList
    }

    private fun clearTexts() {
        this.title.setText("")
        this.subtitle.setText("")
        this.bussiness.setText("")
        this.video.setText("")
        this.url.setText("")
        this.date.setText("")
        this.id.setText("")
    }

    private fun filterResults(query: String) {
        var filteredList = arrayListOf<WebNew>()

        filteredList = listNews.filter { x -> x.title.contains(query) } as ArrayList

        adapter.filter(filteredList)
    }

    private fun setupRecicleView() {
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        adapter = WebNewAdapter(
            listNews,
            onClickDelete = { onDeleteItem(it) },
            onClickListener = { onClickItem(it) })
        binding.rvResults.adapter = adapter
    }

    private fun addElement() {
        listNews.add(
            WebNew(
                this.title.text.toString(),
                this.subtitle.text.toString(),
                this.date.text.toString(),
                2,
                this.video.text.toString(),
                this.url.text.toString(),
                this.bussiness.text.toString()
            )
        )

        adapter.notifyItemInserted(listNews.size - 1)

        clearTexts()

        val toast = Toast.makeText(this, "Item has been created", Toast.LENGTH_LONG)
        toast.show()
    }

    private fun updateElement() {
        if (this.id.text.toString() == "") {
            val toast = Toast.makeText(this, "Select first an item", Toast.LENGTH_LONG)
            toast.show()
            return
        }

        val updateItemIndex = this.id.text.toString().toInt()

        listNews[updateItemIndex].title = this.title.text.toString()
        listNews[updateItemIndex].subtitle = this.subtitle.text.toString()
        listNews[updateItemIndex].bussiness = this.bussiness.text.toString()
        listNews[updateItemIndex].video = this.video.text.toString()
        listNews[updateItemIndex].url = this.url.text.toString()
        listNews[updateItemIndex].publishDate = this.date.text.toString()

        adapter.notifyItemChanged(updateItemIndex)

        clearTexts()

        val toast = Toast.makeText(this, "Item has been updated", Toast.LENGTH_LONG)
        toast.show()
    }

    private fun onClickItem(position: Int) {
        val item = listNews[position]

        this.title.setText(item.title)
        this.subtitle.setText(item.subtitle)
        this.date.setText(item.publishDate)
        this.id.setText("$position")
        this.video.setText(item.video)
        this.url.setText(item.url)
        this.bussiness.setText(item.bussiness)
    }

    private fun onDeleteItem(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("")
            .setTitle("Are you sure want to delete this?")
            .setPositiveButton("Accept") { dialog, which ->
                listNews.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel") { dialog, which -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}