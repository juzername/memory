package nhf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A TopListElement oszt�ly az eredm�nylist�ban megjelen� elemeket kezeli.
 * H�rom fontos attrib�tuma a j�t�kban a k�r�k sz�ma, a j�t�kosn�v �s a helyez�s.
 * A Serializable interf�szt implement�lja a szerializ�lhat�s�g �rdek�ben.
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
 * A Serializable oszt�ly writeObject f�ggv�ny�nek a fel�ldefini�l�sa. Ki�rja a helyez�st, a j�t�kosnevet �s a k�r�k sz�m�t.
 * @param out	Erre az ObjectOutputStreamre �r ki.
 * @throws IOException
 */
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeInt(place);
		out.writeObject(playerName);
		out.writeInt(round);
	}

/**
 * A Serializable oszt�ly readObject f�ggv�ny�nek a fel�ldefini�l�sa. Beolvassa a helyez�st, a j�t�kosnevet �s a k�r�k sz�m�t.
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
 * A TopListElement sz�veges reprezent�ci�ja.
 */
	public String toString() {
		return Integer.toString(place) + ". " + playerName + " " + Integer.toString(round) + " k�r";
	}

/**
 * Be�ll�tja a k�r�k sz�m�t
 * @param r	k�r�k sz�m�nak k�v�nt �rt�ke
 */
	public void setRound(int r) {
		round = r;
	}
	
/**
 * Be�ll�tja a helyez�st.
 * @param p	ezt �ll�tja be helyez�snek
 */
	public void setPlace(int p) {
		place = p;
	}
	
/**
 * Be�ll�tja a j�t�kosnevet.
 * @param p	be�ll�tani k�v�nt j�t�kosn�v (String)
 */
	public void setPlayerName(String p) {
		playerName = p;
	}
	
/**
 * Vissazadja a k�r�k sz�m�t.
 * @return	k�r�k sz�ma
 */
	public int getRound() {
		return round;
	}
	
/**
 * Vissazadja a helyez�st.
 * @return	helyez�s
 */
	public int getPlace() {
		return place;
	}
	
/**
 * Vissazadja a j�t�kosnevet.
 * @return	j�t�kosn�v
 */
	public String getPlayerName() {
		return playerName;
	}
}
