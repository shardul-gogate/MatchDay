package shardulgogate.matchday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PlayerAdapter(private val cont: Context, private val players: ArrayList<Player>): ArrayAdapter<Player>(cont,R.layout.layout_squadlist,players) {

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		return createItemView(position, parent)
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
		return createItemView(position, parent)
	}

	private fun createItemView(position: Int, parent: ViewGroup): View {
		val inflater: LayoutInflater = cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val rowView: View =inflater.inflate(R.layout.layout_squadlist,parent,false)
		val shirtNum: TextView =rowView.findViewById(R.id.shirtnum)
		val firstName: TextView =rowView.findViewById(R.id.firstnamedisp)
		val lastName: TextView =rowView.findViewById(R.id.lastnamedisp)
		firstName.text= players[position].firstName
		lastName.text= players[position].lastName
		shirtNum.text= players[position].shirtNum.toString()
		return rowView
	}
}