package com.evgenynaz.sportnote.note.screens.details

import com.evgenynaz.sportnote.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.evgenynaz.sportnote.note.model.Note
import com.evgenynaz.sportnote.App
import com.evgenynaz.sportnote.databinding.ActivityNoteDetailsBinding
import kotlinx.android.synthetic.main.activity_note_details.*


class NoteDetailsActivity : AppCompatActivity() {
    // private val EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE"
    private lateinit var binding: ActivityNoteDetailsBinding
    private var note: Note? = null
    private var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // вернуться назад
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        title = getString(R.string.note_details_title)
        editText = findViewById(R.id.text)
        if (intent.hasExtra(EXTRA_NOTE)) {
            note = intent.getParcelableExtra(EXTRA_NOTE)
            with(editText) { this?.setText(note?.text) }
        } else {
            note = Note()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> finish()
            R.id.action_save -> if (editText!!.text.isNotEmpty()) {
                note?.text = editText!!.text.toString()
                note?.done = false
                note?.timestamp = System.currentTimeMillis()
                if (intent.hasExtra(EXTRA_NOTE)) {
                    App.instance?.noteDao?.update(note)
                } else {
                    App.instance?.noteDao?.insert(note)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE"
        fun start(caller: Activity, note: Note?) {
            val intent = Intent(caller, NoteDetailsActivity::class.java)
            if (note != null) {
                intent.putExtra(EXTRA_NOTE, note)
            }
            caller.startActivity(intent)
        }
    }
}