package shardulgogate.matchday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_addplayer.*

class AddPlayer : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_addplayer)
		saveplayerbtn.setOnClickListener {
			savePlayer()
		}
		cancelplayer.setOnClickListener {
			cancelEdit()
		}
	}

	override fun onBackPressed() {
		finish()
	}

	fun savePlayer() {
		val firstName=firstnamefield.text.toString()
		val lastName=lastnamefield.text.toString()
		val shirtNum=shirtfield.text.toString().toInt()
		for(p in currTeam.players) {
			if(p.shirtNum==shirtNum) {
				Toast.makeText(this,"Shirt number taken",Toast.LENGTH_SHORT).show()
				return
			}
			if(p.firstName==firstName && p.lastName==lastName) {
				Toast.makeText(this,"Player already exists",Toast.LENGTH_SHORT).show()
				return
			}
		}
		if(firstName.equals("") || lastName.equals("") || shirtNum.equals("")) {
			Toast.makeText(this,"Some fields are empty",Toast.LENGTH_SHORT).show()
			return
		}
		if(matchDayDB.insertPlayer(firstName,lastName,shirtNum, currTeam.teamName)) {
			Toast.makeText(this,"Player added",Toast.LENGTH_SHORT).show()
			currTeam.addPlayer(Player(firstName, lastName, shirtNum))
			if(currTeam==homeTeam)
				homeTeam=currTeam
			else
				awayTeam=currTeam
			finish()
		}
		else
			Toast.makeText(this,"Error adding player",Toast.LENGTH_SHORT).show()
	}

	fun cancelEdit() {
		finish()
	}
}
