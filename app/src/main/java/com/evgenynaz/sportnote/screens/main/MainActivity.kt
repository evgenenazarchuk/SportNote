package com.evgenynaz.sportnote.screens.main

import com.evgenynaz.sportnote.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evgenynaz.sportnote.databinding.ActivityMainBinding
import com.evgenynaz.sportnote.model.Note
import com.evgenynaz.sportnote.screens.details.NoteDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.list)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        with(recyclerView) {
            this?.setLayoutManager(linearLayoutManager)
        }
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = Adapter()
        val with = with(recyclerView) {
            this?.setAdapter(adapter)
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { NoteDetailsActivity.start(this@MainActivity, null) }
        val mainViewModel = ViewModelProviders.of(this).get(
            MainViewModel::class.java
        )
        mainViewModel.noteLiveData!!.observe(this,
            { notes -> adapter.setItems(notes as List<Note?>?) })
    }
}