package shardulgogate.matchday

import android.app.ListActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_viewsquad.*

class ViewSquad : ListActivity(),AdapterView.OnItemLongClickListener {

    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewsquad)
        setUpListView()
        closeListBtn.setOnClickListener {
            finish()
        }
    }

    private fun setUpListView() {
        Toast.makeText(this,"Long click to remove player",Toast.LENGTH_LONG).show()
        playerAdapter=PlayerAdapter(this.applicationContext, currTeam.players)
        val listView= findViewById<ListView>(android.R.id.list)
        listView.adapter=playerAdapter
        listView.isLongClickable=true
        listView.onItemLongClickListener = this
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val confirmDelete: AlertDialog.Builder= AlertDialog.Builder(this)
        confirmDelete.setMessage("Player will be deleted. Action cannot be undone. Proceed?")
        confirmDelete.setPositiveButton("Yes"){ _, _ ->
            if(matchDayDB.deletePlayer(currTeam.players[position])) {
                Toast.makeText(this,"${currTeam.players[position].firstName} ${currTeam.players[position].lastName} removed",Toast.LENGTH_SHORT).show()
                currTeam.removePlayer(position)
                playerAdapter.notifyDataSetChanged()
                if(currTeam==homeTeam)
                    homeTeam=currTeam
                else
                    awayTeam=currTeam
            }
            else {
                Toast.makeText(this,"Error deleting player",Toast.LENGTH_SHORT).show()
            }
        }
        confirmDelete.setNegativeButton("No"){ _, _ -> }
        confirmDelete.show()
        return true
    }
}