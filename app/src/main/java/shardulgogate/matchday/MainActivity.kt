package shardulgogate.matchday

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		splashScreen()
	}

	private fun splashScreen() {
		val splash=thread(start=false) {
			sleep(2000)
			Intent(this, HomeScreen::class.java).also {
				startActivity(it)
			}
			finish()
		}
		splash.start()
	}
}