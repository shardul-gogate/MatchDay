package shardulgogate.matchday

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(val context: Context):SQLiteOpenHelper(context,"matchDay.db",null,1) {

	private val TEAM_TABLE: String="TEAM_TABLE"
	private val TEAM_ID: String="TEAM_ID"
	private val TEAM_NAME: String="TEAM_NAME"
	private val PLAYER_TABLE: String="PLAYER_TABLE"
	private val PLAYER_ID: String="PLAYER_ID"
	private val PLAYER_FIRST: String="PLAYER_FIRST_NAME"
	private val PLAYER_LAST: String="PLAYER_LAST_NAME"
	private val PLAYER_SHIRT: String="PLAYER_SHIRT"

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL("CREATE TABLE IF NOT EXISTS $TEAM_TABLE($TEAM_ID INTEGER PRIMARY KEY AUTOINCREMENT,$TEAM_NAME TEXT)")
		db.execSQL("CREATE TABLE IF NOT EXISTS $PLAYER_TABLE($PLAYER_ID INTEGER PRIMARY KEY AUTOINCREMENT,$PLAYER_FIRST TEXT,$PLAYER_LAST TEXT,$PLAYER_SHIRT INTEGER,$TEAM_ID INTEGER REFERENCES $TEAM_TABLE($TEAM_ID))")
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL("DROP TABLE IF EXISTS $TEAM_TABLE")
		db.execSQL("DROP TABLE IF EXISTS $PLAYER_TABLE")
		onCreate(db)
	}

	fun initializeLocalData(): Int {
		val db: SQLiteDatabase=this.writableDatabase
		val result: Cursor=db.rawQuery("SELECT * FROM $TEAM_TABLE",null)
		if(result.count!=2) {
			return 1
		}
		result.moveToFirst()
		homeTeam.teamName=result.getString(1)
		var players=this.getAllPlayers(result.getInt(0))
		if(players.count==0) {
			return 2
		}
		while(players.moveToNext()) {
			val fname=players.getString(1)
			val lname=players.getString(2)
			val shirtNum=players.getInt(3)
			homeTeam.addPlayer(Player(fname,lname,shirtNum))
		}
		result.moveToNext()
		awayTeam.teamName=result.getString(1)
		players=this.getAllPlayers(result.getInt(0))
		if(players.count==0) {
			return 3
		}
		while(players.moveToNext()) {
			val fname=players.getString(1)
			val lname=players.getString(2)
			val shirtNum=players.getInt(3)
			awayTeam.addPlayer(Player(fname,lname,shirtNum))
		}
		result.close()
		return 0
	}

	private fun getAllPlayers(teamID: Int): Cursor {
		val db: SQLiteDatabase=this.writableDatabase
		return db.rawQuery("SELECT * FROM $PLAYER_TABLE WHERE $TEAM_ID=?", arrayOf("$teamID"))
	}

	fun updateTeamName(oldTeamName: String, teamNameUpdated: String): Boolean {
		val contentValues=ContentValues()
		val db: SQLiteDatabase=this.writableDatabase
		val oldTeamID=getTeamID(oldTeamName)
		if(oldTeamID==-1)
			return false
		contentValues.put(TEAM_ID,oldTeamID)
		contentValues.put(TEAM_NAME,teamNameUpdated)
		val success=db.update(TEAM_TABLE,contentValues,"$TEAM_ID=?", arrayOf("$oldTeamID"))
		return success==1
	}

	private fun getTeamID(teamNameQuery: String): Int {
		val db: SQLiteDatabase=this.writableDatabase
		val result: Cursor=db.rawQuery("SELECT * FROM $TEAM_TABLE WHERE $TEAM_NAME=?",arrayOf(teamNameQuery))
		if(result.count!=1)
			return -1
		result.moveToFirst()
		val teamIDFound=result.getInt(0)
		result.close()
		return teamIDFound
	}

	fun insertPlayer(firstName: String, lastName:String, shirtNum: Int, playerTeamName: String): Boolean {
		val contentValues=ContentValues()
		val db: SQLiteDatabase=this.writableDatabase
		val playerTeamID: Int=getTeamID(playerTeamName)
		contentValues.put(PLAYER_FIRST,firstName)
		contentValues.put(PLAYER_LAST,lastName)
		contentValues.put(PLAYER_SHIRT,shirtNum)
		contentValues.put(TEAM_ID,playerTeamID)
		val success: Long=db.insert(PLAYER_TABLE,null,contentValues)
		return success.toInt() != -1
	}

	fun deletePlayer(player: Player): Boolean {
		val db: SQLiteDatabase=this.writableDatabase
		val playerSearchID=getPlayerID(player.firstName,player.lastName)
		if(playerSearchID==-1)
			return false
		val success=db.delete(PLAYER_TABLE,"$PLAYER_ID=?", arrayOf("$playerSearchID"))
		return success==1
	}

	private fun getPlayerID(firstNameSearch: String, lastNameSearch: String): Int {
		val db: SQLiteDatabase=this.writableDatabase
		val result: Cursor=db.rawQuery("SELECT $PLAYER_ID FROM $PLAYER_TABLE WHERE $PLAYER_FIRST=? AND $PLAYER_LAST=?",arrayOf(firstNameSearch,lastNameSearch))
		if(result.count!=1)
			return -1
		result.moveToFirst()
		val playerID=result.getInt(0)
		result.close()
		return playerID
	}

	fun createTeam(teamNameCreated: String): Boolean {
		val contentValues=ContentValues()
		val db: SQLiteDatabase=this.writableDatabase
		contentValues.put(TEAM_NAME,teamNameCreated)
		val success: Long=db.insert(TEAM_TABLE,null,contentValues)
		return success.toInt() != -1
	}
}