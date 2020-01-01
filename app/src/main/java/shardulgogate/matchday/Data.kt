package shardulgogate.matchday

class Team(var teamName: String) {
	var players= arrayListOf<Player>()
	var goals: Int=0

	fun addPlayer(player: Player) {
		players.add(player)
	}

	fun removePlayer(index: Int) {
		players.removeAt(index)
	}

	fun resetStats() {
		goals=0
		for(p in players) {
			p.resetStats()
		}
	}

	init {
		players.ensureCapacity(11)
	}
}

class Player(val firstName: String,val lastName: String, val shirtNum: Int) {
	var isSentOff: Boolean=false
	var isBooked: Boolean=false
	var goals: Int=0

	fun resetStats() {
		isSentOff=false
		isBooked=false
		goals=0
	}
}

class Event(val minute: Int,val eventType: String,val player: Player,val eventTeam: Team)

var matchTime: Int=50
var matchTimeModified: Boolean=false
var matchMins: Int=0

var homeTeam=Team("Home")
var awayTeam=Team("Away")

lateinit var matchDayDB: DatabaseHelper

var currTeam=Team("")

val event: ArrayList<Event> = arrayListOf()