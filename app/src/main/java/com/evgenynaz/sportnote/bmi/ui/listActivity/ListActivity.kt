package com.evgenynaz.sportnote.bmi.ui.listActivity


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evgenynaz.sportnote.R
import com.evgenynaz.sportnote.bmi.Common.LIST_TO_EDIT_ACTIVITY_INTENT_ID
import com.evgenynaz.sportnote.bmi.database.UserBmi
import com.evgenynaz.sportnote.bmi.ui.insertEditActivity.InsertEditActivity
import com.evgenynaz.sportnote.databinding.ActivityListBinding
import com.google.android.material.snackbar.Snackbar


class ListActivity(override var recyclerView: RecyclerView?) : AppCompatActivity(),
BmiAdapter.BmiAdapterOnClickHandler {

    lateinit var binding: ActivityListBinding

    lateinit var mUserBmiList: MutableList<UserBmi>

    lateinit var bmiAdapter: BmiAdapter

    lateinit var tempUserBmi: UserBmi

    private lateinit var listViewModel: ListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAllView()

        binding.mFab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))

        binding.mFab.setOnClickListener { startActivity(Intent(this, InsertEditActivity::class.java)) }


        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)


        val snackbarCallback = object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                listViewModel.deleteBmiData(tempUserBmi)
                bmiAdapter.notifyDataSetChanged()
                checkDataList()
            }

            override fun onShown(sb: Snackbar?) {}
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder!!.itemView.tag
                tempUserBmi = mUserBmiList[position as Int]
                mUserBmiList.removeAt(position)
                bmiAdapter.notifyDataSetChanged()
                checkDataList()
                val snackbar = Snackbar.make(binding.coordinatorLayout, "Deleted 1", Snackbar.LENGTH_LONG)
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.addCallback(snackbarCallback)
                snackbar.setAction("Undo") {
                    mUserBmiList.add(position, tempUserBmi)
                    bmiAdapter.notifyDataSetChanged()
                    snackbar.removeCallback(snackbarCallback)
                    checkDataList()
                }
                snackbar.show()
            }
        }).attachToRecyclerView(recyclerView)


/*        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if ((dy > 0) and fab.isShown)
                    fab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show()
            }
        })*/
//        listActivityPres.getUpdateList()
        listViewModel.getUpdateList().observe(this, Observer {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                mUserBmiList = it
                checkDataList()
                bmiAdapter.swapList(it)
            }
        })
    }

    private fun bindAllView() {

        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        recyclerView?.layoutManager = layoutManager

        bmiAdapter = BmiAdapter(this, this)

        recyclerView?.adapter = bmiAdapter


        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView != null) {
                    super.onScrolled(recyclerView, dx, dy)
                }
                if (dy > 0 && mUserBmiList.size != 0)
                    binding.mFab.visibility = View.GONE
                else
                    binding.mFab.visibility = View.VISIBLE
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (recyclerView != null) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING && mUserBmiList.size != 0) {
                    binding.mFab.visibility = View.GONE
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    binding.mFab.visibility = View.VISIBLE
                }
            }
        })


    }


    override fun onClick(id: Int) {
        val intent = Intent(this, InsertEditActivity::class.java)
        intent.putExtra(LIST_TO_EDIT_ACTIVITY_INTENT_ID, id)
        startActivity(intent)
    }

    fun checkDataList() {
        if (mUserBmiList.size == 0)
        binding.emptyViewText.visibility = View.VISIBLE
        else
            binding.emptyViewText.visibility  = View.GONE
    }

}
