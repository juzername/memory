package nhf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A TopListElement osztály az eredménylistában megjelenõ elemeket kezeli.
 * Három fontos attribútuma a játékban a körök száma, a játékosnév és a helyezés.
 * A Serializable interfészt implementálja a szerializálhatóság érdekében.
 * @author Sivado
 *
 */
public class TopListElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int round;
	private String playerName;
	private int place;
	
	public TopListElement(int r, int p, String pl) {
		round = r;
		place = p;
		playerName = pl;
	}
/**
 * A Serializable osztály writeObject függvényének a felüldefiniálása. Kiírja a helyezést, a játékosnevet és a körök számát.
 * @param out	Erre az ObjectOutputStreamre ír ki.
 * @throws IOException
 */
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeInt(place);
		out.writeObject(playerName);
		out.writeInt(round);
	}

/**
 * A Serializable osztály readObject függvényének a felüldefiniálása. Beolvassa a helyezést, a játékosnevet és a körök számát.
 * @param in	innen olvas be
 * @throws IOException
 * @throws ClassNotFoundException
 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		place = in.readInt();
		playerName = in.readObject().toString();
		round = in.readInt();
	}

/**
 * A TopListElement szöveges reprezentációja.
 */
	public String toString() {
		return Integer.toString(place) + ". " + playerName + " " + Integer.toString(round) + " kör";
	}

/**
 * Beállítja a körök számát
 * @param r	körök számának kívánt értéke
 */
	public void setRound(int r) {
		round = r;
	}
	
/**
 * Beállítja a helyezést.
 * @param p	ezt állítja be helyezésnek
 */
	public void setPlace(int p) {
		place = p;
	}
	
/**
 * Beállítja a játékosnevet.
 * @param p	beállítani kívánt játékosnév (String)
 */
	public void setPlayerName(String p) {
		playerName = p;
	}
	
/**
 * Vissazadja a körök számát.
 * @return	körök száma
 */
	public int getRound() {
		return round;
	}
	
/**
 * Vissazadja a helyezést.
 * @return	helyezés
 */
	public int getPlace() {
		return place;
	}
	
/**
 * Vissazadja a játékosnevet.
 * @return	játékosnév
 */
	public String getPlayerName() {
		return playerName;
	}
}
