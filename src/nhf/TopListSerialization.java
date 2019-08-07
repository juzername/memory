package nhf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A TopListSerialization oszt�ly a TopListElement objektumokat dolgozza fel; f�jlba �r, f�jlb�l olvas, ill. friss�ti a f�jlt.
 * A f�jl tartalmazza az eredm�nylist�t.
 * A TopListElement objektumokat egy ArrayListben t�rolja.
 * A Comparator interf�szt implement�lja a rendezhet�s�g �rdek�ben.
 * @author Sivado
 *
 */
public class TopListSerialization implements Comparator<TopListElement> {
	public List<TopListElement> toplist = new ArrayList<TopListElement>();
	private TopListElement tle;
	
	public TopListSerialization(TopListElement elem) {
		tle = elem;
	}

/**
 * Egy f�jlba ki�rja a toplist elemeit.
 * @param filename	ebb�l a Stringb�l lesz egy FileOutputStream, ebbe a f�jlba �rja ki az adatokat.
 */
	public void writeToFile(String filename) {
		try {
			FileOutputStream fops = new FileOutputStream(filename);
			ObjectOutputStream oops = new ObjectOutputStream(fops);
			oops.writeObject(toplist);
			oops.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/**
 * Egy f�jlb�l beolvassa a toplist list�ba a TopListElemeket.
 * @param filename	a f�jln�v, ahonnan beolvas
 * @throws IOException
 */
	@SuppressWarnings("unchecked")
	public void readFromFile(String filename) throws IOException {
			FileInputStream fips = new FileInputStream(filename);
			@SuppressWarnings("resource")
			ObjectInputStream oips = new ObjectInputStream(fips);
			try {
				toplist = (ArrayList<TopListElement>) oips.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
/**
 * Az eredm�nylista frissit�s�hez �rt f�ggv�ny.
 * Beolvassa a f�jlb�l a toplist elemeit, hozz�adja a j�t�k �ltal gener�lt �j eredm�nyt jelz� TopListElementet,
 * majd rendezi a list�t �gy, hogy a legkisebb helyez�s legyen legel�l, �s a helyez�seket friss�ti.
 * V�g�l a m�dos�tott eredm�nylist�t vissza�rja a f�jlba.
 * @param filename
 * @param tle
 */
	public void updateFile(String filename, TopListElement tle) {
		try {
			readFromFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.tle = tle;
		toplist.add(tle);
		Collections.sort(toplist, this);
		
		for(int i = 0; i < toplist.size(); i++) {
			TopListElement t = toplist.get(i);
			
			if(i == 0)
				t.setPlace(1);
			else {
				TopListElement before = toplist.get(i-1);
		
				if(t.getRound()==before.getRound())
					t.setPlace(before.getPlace());
				else
					t.setPlace(before.getPlace()+1);
			}
			toplist.set(i, t);
		}
		
		writeToFile(filename);
	}

/**
 * A Comparator interf�sz implement�l�sakor a compare f�ggv�nyt fel�l kell defini�lni.
 * K�t TopListElementet hasonl�t �ssze az alapj�n, hogy a k�r�k sz�ma kin�l volt kevesebb.
 * (Min�l kevesebb, ann�l el�bb van a sorban a helyez�seket n�zve.)
 * @param t1	az els� TopListElement, amit �ssze szeretn�nk hasonl�tani
 * @param t2	a m�sodik TopListElement, amit �ssze szeretn�nk hasonl�tani
 * @return	0, ha egyenl�ek,
 * 			-1, ha az els�n�l a k�r�k sz�ma t�bb
 * 			+1, egy�bk�nt
 */
	@Override
	public int compare(TopListElement t1, TopListElement t2) {
		int tr1 = t1.getRound();
		int tr2 = t2.getRound();
		if(tr1 == tr2)
			return 0;
		if(tr1 < tr2)
			return -1;
		else
			return 1;
	}
}
