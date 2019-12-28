package shardulgogate.matchday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_duration.*

class Duration : AppCompatActivity() {

	private val maxTime=90
	private val minTime=10
	private var currTime= matchTime

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_duration)
		timeDisp.text=currTime.toString()
		decrFive.setOnClickListener {
			decrFive()
		}
		decrTen.setOnClickListener {
			decrTen()
		}
		incrFive.setOnClickListener {
			incrFive()
		}
		incrTen.setOnClickListener {
			incrTen()
		}
		settimebtn.setOnClickListener {
			setMatchTime()
		}
	}

	private fun decrTen() {
		if(currTime<=(minTime+5)) {
			Toast.makeText(this,"Minimum time $minTime minutes", Toast.LENGTH_SHORT).show()
			currTime=minTime
			timeDisp.text=currTime.toString()
		}
		else {
			currTime-=10
			timeDisp.text=currTime.toString()
		}
	}

	private fun decrFive() {
		if(currTime==minTime) {
			Toast.makeText(this,"Minimum time $minTime minutes", Toast.LENGTH_SHORT).show()
		}
		else {
			currTime-=5
			timeDisp.text=currTime.toString()
		}
	}

	private fun incrFive() {
		if(currTime==maxTime) {
			Toast.makeText(this,"Maximum time $maxTime minutes", Toast.LENGTH_SHORT).show()
		}
		else {
			currTime+=5
			timeDisp.text=currTime.toString()
		}
	}

	private fun incrTen() {
		if(currTime>=(maxTime-5)) {
			Toast.makeText(this,"Maximum time $maxTime minutes", Toast.LENGTH_SHORT).show()
			currTime=maxTime
			timeDisp.text=currTime.toString()
		}
		else {
			currTime+=10
			timeDisp.text=currTime.toString()
		}
	}

	private fun setMatchTime() {
		matchTime=currTime
		matchTimeModified=true
		finish()
	}
}
