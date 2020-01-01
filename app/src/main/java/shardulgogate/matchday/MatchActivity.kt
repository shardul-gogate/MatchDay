package shardulgogate.matchday

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity(),Runnable {

	var millis: Long=0
	var seconds: Int=0
	var startTime: Long=0
	lateinit var handler: Handler
	var extraMills: Long=0
	var lastPauseTime: Long=0
	var matchPaused=false
	var extraTime=0
	var extraSeconds=0
	var extraMins=0
	var inExtraTime=false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_match)
		handler=Handler()
		resumeBtn.isEnabled=false
		pauseBtn.setOnClickListener {
			pauseCode()
		}
		resumeBtn.setOnClickListener {
			resumeCode()
		}
		addEvent.setOnClickListener {
			addEvent()
		}
		quitBtn.setOnClickListener {
			quit()
		}
		highlights.setOnClickListener {
			showHighlights()
		}
		startTime=SystemClock.uptimeMillis()
		handler.postDelayed(this,0)
	}

	override fun onStart() {
		super.onStart()
		resumeCode()
	}

	/*override fun onStop() {
		super.onStop()
		pauseCode()
	}*/

	override fun run() {
		updateScorecard()
		millis=SystemClock.uptimeMillis()-startTime-extraMills
		seconds=(millis/1000).toInt()
		matchMins=seconds/60
		seconds%=60
		if(!inExtraTime) {
			extraSeconds=(extraMills/1000).toInt()
			extraTime=extraSeconds/60
			extraSeconds%=60
			if(extraSeconds>=25)
				extraTime+=1
		}
		etdisplay.text="ETC:$extraTime"

		if(matchMins< matchTime) {
			clockView.text="$matchMins:$seconds"
			inExtraTime=false
			handler.postDelayed(this,1)
		}
		else {
			if(extraMins<extraTime) {
				inExtraTime=true
				extraMins= matchMins- matchTime
				clockView.text="$matchMins+$extraMins:$seconds"
				handler.postDelayed(this,0)
			}
			else {
				clockView.text="$matchTime+$extraTime:00"
				resumeBtn.isEnabled=false
				pauseBtn.isEnabled=false
				matchPaused=true
				handler.removeCallbacks(this)
			}
		}
	}

	override fun onBackPressed() {
		quit()
	}

	private fun updateScorecard() {
		if(event.isNotEmpty()) {
			scorecard.text="${homeTeam.goals}-${awayTeam.goals}"
			for(e in event.asReversed())
				if(e.eventType.equals("goal")) {
					goalMin.text="${e.minute}' - "
					goalScorer.text="${e.player.firstName} ${e.player.lastName}"
					break
				}
		}
	}

	private fun pauseCode() {
		if(!matchPaused) {
			handler.removeCallbacks(this)
			lastPauseTime=SystemClock.uptimeMillis()
			resumeBtn.isEnabled=true
			pauseBtn.isEnabled=false
			matchPaused=true
		}
	}

	private fun resumeCode() {
		if(matchPaused) {
			resumeBtn.isEnabled=false
			pauseBtn.isEnabled=true
			matchPaused=false
			extraMills+=(SystemClock.uptimeMillis()-lastPauseTime)
			handler.postDelayed(this,0)
		}
	}

	private fun showHighlights() {
		pauseCode()
		Intent(this,HighlightsActivity::class.java).also {
			startActivity(it)
		}
	}

	private fun quit() {
		val confirmQuit: AlertDialog.Builder=AlertDialog.Builder(this)
		confirmQuit.setMessage("Do you want to quit the match?")
		confirmQuit.setPositiveButton("Yes"){ _, _ ->
			showHighlights()
			homeTeam.resetStats()
			awayTeam.resetStats()
			event.clear()
			finish()
		}
		confirmQuit.setNegativeButton("No"){ _, _ -> }
		confirmQuit.show()
	}

	private fun addEvent() {
		pauseCode()
		Intent(this,EventActivity::class.java).also {
			startActivity(it)
		}
	}
}