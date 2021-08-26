package com.evgenynaz.sportnote.note

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evgenynaz.sportnote.R
import com.evgenynaz.sportnote.databinding.ActivityBmiBinding
import com.evgenynaz.sportnote.databinding.ActivityNoteBinding
import com.evgenynaz.sportnote.note.*
import com.evgenynaz.sportnote.note.database.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_bmi.toolbar_bmi_activity
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class ActivityNote : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    private lateinit var binding: ActivityNoteBinding

    lateinit var viewModal: NoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
       setSupportActionBar(toolbar_note)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // вернуться назад
        binding.toolbarNote.setNavigationOnClickListener {
            onBackPressed()
        }

        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)


        notesRV.layoutManager = LinearLayoutManager(this)


        val noteRVAdapter = NoteRVAdapter(this, this, this)


        notesRV.adapter = noteRVAdapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)


        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {

                noteRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {

            val intent = Intent(this@ActivityNote, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {

        val intent = Intent(this@ActivityNote, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {

        viewModal.deleteNote(note)

        Toast.makeText(this, "${note.noteTitle} Заметка удалена", Toast.LENGTH_LONG).show()
    }
}