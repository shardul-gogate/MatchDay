package shardulgogate.matchday

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homescreen.*

class HomeScreen : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_homescreen)
		matchDayDB= DatabaseHelper(this)
		val errorCode= matchDayDB.initializeLocalData()
		val dbLoadError: AlertDialog.Builder=AlertDialog.Builder(this)
		dbLoadError.setNeutralButton("Ok"){ _, _ -> }
		when(errorCode) {
			0 -> dbLoadError.setMessage("Database Loaded")
			1 -> dbLoadError.setMessage("No Teams in Database")
			2 -> dbLoadError.setMessage("No Players in Home Team")
			3 -> dbLoadError.setMessage("No Player in Away Team")
		}
		when(errorCode) {
			1,2,3 -> {
				matchDayDB.createTeam("Home")
				matchDayDB.createTeam("Away")
			}
		}
		dbLoadError.show()
		hometeambtn.setOnClickListener {
			homeSquadInfo()
		}
		awayteambtn.setOnClickListener {
			awaySquadInfo()
		}
		durationbtn.setOnClickListener {
			setMatchTime()
		}
		kickoffbtn.setOnClickListener {
			startMatch()
		}
	}

	private fun homeSquadInfo() {
		currTeam= homeTeam
		Intent(this,SquadInfo::class.java).also {
			startActivity(it)
		}
	}

	private fun awaySquadInfo() {
		currTeam= awayTeam
		Intent(this,SquadInfo::class.java).also {
			startActivity(it)
		}
	}

	private fun setMatchTime() {
		Intent(this, Duration::class.java).also {
			startActivity(it)
		}
	}

	private fun startMatch() {
		Intent(this, MatchActivity::class.java).also {
			startActivity(it)
		}
	}
}
