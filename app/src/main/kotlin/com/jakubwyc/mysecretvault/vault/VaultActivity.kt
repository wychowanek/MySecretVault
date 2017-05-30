package com.jakubwyc.mysecretvault.vault

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.jakewharton.rxbinding.view.RxView
import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.SystemContext
import com.jakubwyc.mysecretvault.model.Treasure
import com.jakubwyc.mysecretvault.treasure.EditTreasureActivity
import com.jakubwyc.mysecretvault.treasure.EditTreasureActivity.Companion.RESULT_TREASURE_SAVED
import com.jakubwyc.mysecretvault.treasure.TreasureAdapter
import com.jakubwyc.mysecretvault.treasure.TreasureOnClickListener
import kotlinx.android.synthetic.main.activity_vault.*

const val NEW_TREASURE_REQUEST = 123

class VaultActivity : AppCompatActivity(), VaultView, TreasureOnClickListener {

    private lateinit var presenter: VaultPresenter
    private var adapter: TreasureAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        setupRecyclerView()

        presenter = VaultPresenter(applicationContext as SystemContext)
        presenter.attachView(this)
        presenter.onStart()

        RxView.clicks(fab).subscribe { presenter.onNewTreasureClicked() }
    }

    private fun setupRecyclerView() {
        recyclerView.isNestedScrollingEnabled = false
        val layoutManager = object : LinearLayoutManager(this) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return true
            }
        }
        recyclerView.layoutManager = layoutManager
        val itemTouchHelper = ItemTouchHelper(itemSwipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun goToEditTreasureScreen(treasure: Treasure?) {
        val intent = Intent(this, EditTreasureActivity::class.java)
        if (treasure != null) {
            intent.putExtra(getString(R.string.extra_treasure), treasure)
        }
        startActivityForResult(intent, NEW_TREASURE_REQUEST)
    }

    override fun showSnackbar(messageId: Int) {
        Snackbar.make(parentView, messageId, Snackbar.LENGTH_SHORT).show()
    }

    override fun renderTreasures(treasures: List<Treasure>) {
        adapter = TreasureAdapter(treasures.toMutableList(), this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NEW_TREASURE_REQUEST) {
            if (resultCode == RESULT_TREASURE_SAVED) {
                showSnackbar(R.string.treasure_saved)
            }
        }
    }

    override fun onTreasureClick(treasure: Treasure) {
        presenter.onEditTreasureClicked(treasure)
    }

    internal val itemSwipeCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val adapterPosition = viewHolder.adapterPosition
            val treasureSwiped = adapter!!.getTreasureAt(adapterPosition)
            presenter.addCandidateToRemove(treasureSwiped)
            adapter!!.removeTreasure(treasureSwiped, adapterPosition)

            Snackbar.make(parentView, R.string.treasure_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.toast_undo) {
                        presenter.removeCandidateToRemove(treasureSwiped)
                        adapter!!.addTreasure(treasureSwiped, adapterPosition)
                    }
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(snackbar: Snackbar?, event: Int) {
                            when (event) {
                                DISMISS_EVENT_CONSECUTIVE -> Unit
                                else -> presenter.removeTreasures()
                            }
                        }
                    }).show()
        }
    }
}
