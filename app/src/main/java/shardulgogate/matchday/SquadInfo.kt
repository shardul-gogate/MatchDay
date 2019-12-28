package shardulgogate.matchday

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_squadinfo.*

class SquadInfo : AppCompatActivity() {

	private lateinit var oldTeamName: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_squadinfo)
		setUpScreen()
		oldTeamName=currTeam.teamName
		viewSquadBtn.setOnClickListener {
			viewSquad()
		}
		addPlayerBtn.setOnClickListener {
			addPlayer()
		}
		closeBtn.setOnClickListener {
			close()
		}
	}

	override fun onBackPressed() {
		close()
	}

	private fun setUpScreen() {
		teamName.setText(currTeam.teamName)
		when(currTeam) {
			homeTeam -> {
				teamCrest.setImageResource(R.drawable.hometeam)
				divisionView.setBackgroundColor(resources.getColor(R.color.colorSecondary))
			}
			awayTeam -> {
				teamCrest.setImageResource(R.drawable.awayteam)
				divisionView.setBackgroundColor(resources.getColor(R.color.colorAccent))
			}
		}
	}

	private fun viewSquad() {
		Intent(this, ViewSquad::class.java).also {
			startActivity(it)
		}
	}

	private fun addPlayer() {
		Intent(this, AddPlayer::class.java).also {
			startActivity(it)
		}
	}

	private fun close() {
		val newTeamName=teamName.text.toString()
		if(newTeamName.equals(oldTeamName)) {
			finish()
		}
		val teamUpdateStatus: AlertDialog.Builder=AlertDialog.Builder(this)
		if(validSquad() && validTeamName(newTeamName)) {
			if(matchDayDB.updateTeamName(oldTeamName, newTeamName)) {
				currTeam.teamName=newTeamName
				if(currTeam==homeTeam)
					homeTeam=currTeam
				else
					awayTeam=currTeam
				teamUpdateStatus.setMessage("Team Updated")
				teamUpdateStatus.setNeutralButton("Ok"){ _,_ ->
					finish()
				}
				teamUpdateStatus.show()
			}
			else {
				teamUpdateStatus.setMessage("Error updating Team")
				teamUpdateStatus.setNeutralButton("Ok"){ _,_ -> }
				teamUpdateStatus.show()
			}
		}
	}

	private fun validTeamName(newTeamName: String): Boolean {
		if(newTeamName.equals("")) {
			Toast.makeText(this,"Team name empty",Toast.LENGTH_SHORT).show()
			return false
		}
		return true
	}

	private fun validSquad(): Boolean {
		if(currTeam.players.size==0) {
			Toast.makeText(this,"No Players in team",Toast.LENGTH_SHORT).show()
			return false
		}
		return true
	}
}
