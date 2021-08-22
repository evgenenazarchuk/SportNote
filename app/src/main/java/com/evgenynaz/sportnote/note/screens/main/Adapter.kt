package com.evgenynaz.sportnote.note.screens.main

import android.app.Activity
import android.graphics.Paint
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import com.evgenynaz.sportnote.note.model.Note
import com.evgenynaz.sportnote.note.screens.details.NoteDetailsActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.SortedList
import com.evgenynaz.sportnote.note.App
import com.evgenynaz.sportnote.R


class Adapter : RecyclerView.Adapter<Adapter.NoteViewHolder>() {
    private val sortedList: SortedList<Note> =
        SortedList(Note::class.java, object : SortedList.Callback<Note>() {
            override fun compare(o1: Note, o2: Note): Int {
                if (!o2.done && o1.done) {
                    return 1
                }
                return if (o2.done && !o1.done) {
                    -1
                } else (o2.timestamp.toInt() - o1.timestamp.toInt())
            }

            override fun onChanged(position: Int, count: Int) {
                notifyItemRangeChanged(position, count)
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areItemsTheSame(item1: Note, item2: Note): Boolean {
                return item1.uid === item2.uid
            }

            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }
        })

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_list, parent, false)
        )
    }

    override fun onBindViewHolder(@NonNull holder: NoteViewHolder, position: Int) {
        holder.bind(sortedList[position])
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun setItems(notes: List<Note?>?) {
        if (notes != null) {
            sortedList.replaceAll(notes)
        }
    }

    class NoteViewHolder(@NonNull itemView: View) : ViewHolder(itemView) {
        var noteText: TextView
        var completed: CheckBox
        var delete: View
        var note: Note? = null
        var silentUpdate = false
        fun bind(note: Note) {
            this.note = note
            noteText.setText(note.text)
            updateStrokeOut()
            silentUpdate = true
            completed.isChecked = note.done
            silentUpdate = false
        }

        private fun updateStrokeOut() {
            if (note?.done == true) {
                noteText.paintFlags = noteText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                noteText.paintFlags = noteText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        init {
            noteText = itemView.findViewById(R.id.note_text)
            completed = itemView.findViewById(R.id.completed)
            delete = itemView.findViewById(R.id.delete)
            itemView.setOnClickListener {
                NoteDetailsActivity.start(
                    (itemView.getContext() as Activity),
                    note
                )
            }
            delete.setOnClickListener { App.instance?.noteDao?.delete(note) }
            completed.setOnCheckedChangeListener { compoundButton, checked ->
                if (!silentUpdate) {
                    note?.done ?: checked
                    App.instance?.noteDao?.update(note)
                }
                updateStrokeOut()
            }
        }
    }

}