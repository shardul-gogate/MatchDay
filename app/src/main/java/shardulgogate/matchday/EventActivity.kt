package shardulgogate.matchday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

	private lateinit var awayAdapter: PlayerAdapter
	private lateinit var homeAdapter: PlayerAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_event)
		homeAdapter= PlayerAdapter(this, homeTeam.players)
		awayAdapter= PlayerAdapter(this, awayTeam.players)
		playerdropdown.adapter=homeAdapter
		hometeamradio.isChecked=true
		currTeam= homeTeam
		hometeamradio.setOnClickListener{
			setHome()
		}
		awayteamradio.setOnClickListener {
			setAway()
		}
		selectplayer.setOnClickListener {
			verifySelection()
		}
	}

	private fun setHome() {
		currTeam= homeTeam
		playerdropdown.adapter=homeAdapter
	}

	private fun setAway() {
		currTeam= awayTeam
		playerdropdown.adapter=awayAdapter
	}

	private fun verifySelection() {
		val player=playerdropdown.selectedItem as Player
		if(player.isSentOff) {
			Toast.makeText(this,"Cannot select a sent off player",Toast.LENGTH_SHORT).show()
			return
		}
		if(goalradio.isChecked) {
			player.goals+=1
			currTeam.goals+=1
			event.add(Event(matchMins+1,"goal",player))
		}
		else if(yellowradio.isChecked) {
			if(player.isBooked) {
				player.isSentOff=true
				event.add(Event(matchMins+1,"red",player))
			}
			else {
				player.isBooked=true
				event.add(Event(matchMins+1,"yellow",player))
			}
		}
		else if(redradio.isChecked) {
			player.isSentOff=true
			event.add(Event(matchMins+1,"red",player))
		}
		else {
			Toast.makeText(this,"No event selected",Toast.LENGTH_SHORT).show()
			return
		}
		finish()
	}
}