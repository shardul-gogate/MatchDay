package shardulgogate.matchday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EventsAdapter(private val cont: Context, private val events: ArrayList<Event>): ArrayAdapter<Event>(cont,R.layout.layout_highlights,events) {
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val inflater: LayoutInflater = cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val rowView: View =inflater.inflate(R.layout.layout_highlights,parent,false)
		val homePlayer: TextView =rowView.findViewById(R.id.homeplayer)
		homePlayer.text=""
		val awayPlayer: TextView=rowView.findViewById(R.id.awayplayer)
		awayPlayer.text=""
		val eventTypeMinute: TextView=rowView.findViewById(R.id.eventtypeminute)
		val eventPlayer: Player=events[position].player
		val eventPlayerName="${eventPlayer.firstName} ${eventPlayer.lastName}"
		val eventTeam=events[position].eventTeam
		lateinit var eventTypeInit: String
		when(eventTeam) {
			homeTeam -> homePlayer.text=eventPlayerName
			awayTeam -> awayPlayer.text=eventPlayerName
		}
		when(events[position].eventType) {
			"goal" -> eventTypeInit="(G)"
			"yellow" -> eventTypeInit="(Y)"
			"red" -> eventTypeInit="(R)"
		}
		eventTypeMinute.text="$eventTypeInit(${events[position].minute})"
		return rowView
	}
}