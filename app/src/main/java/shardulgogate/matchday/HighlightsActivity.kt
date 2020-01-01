package shardulgogate.matchday

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_highlights.*

class HighlightsActivity : Activity() {

	private lateinit var eventAdapter: EventsAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_highlights)
		eventAdapter= EventsAdapter(this.applicationContext,event)
		val listView: ListView= findViewById<ListView>(R.id.highlightslist)
		listView.adapter=eventAdapter
		closehighlights.setOnClickListener {
			finish()
		}
	}
}
