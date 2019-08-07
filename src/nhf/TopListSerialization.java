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
 * A TopListSerialization osztály a TopListElement objektumokat dolgozza fel; fájlba ír, fájlból olvas, ill. frissíti a fájlt.
 * A fájl tartalmazza az eredménylistát.
 * A TopListElement objektumokat egy ArrayListben tárolja.
 * A Comparator interfészt implementálja a rendezhetõség érdekében.
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
 * Egy fájlba kiírja a toplist elemeit.
 * @param filename	ebbõl a Stringbõl lesz egy FileOutputStream, ebbe a fájlba írja ki az adatokat.
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
 * Egy fájlból beolvassa a toplist listába a TopListElemeket.
 * @param filename	a fájlnév, ahonnan beolvas
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
 * Az eredménylista frissitéséhez írt függvény.
 * Beolvassa a fájlból a toplist elemeit, hozzáadja a játék által generált új eredményt jelzõ TopListElementet,
 * majd rendezi a listát úgy, hogy a legkisebb helyezés legyen legelöl, és a helyezéseket frissíti.
 * Végül a módosított eredménylistát visszaírja a fájlba.
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
 * A Comparator interfész implementálásakor a compare függvényt felül kell definiálni.
 * Két TopListElementet hasonlít össze az alapján, hogy a körök száma kinél volt kevesebb.
 * (Minél kevesebb, annál elõbb van a sorban a helyezéseket nézve.)
 * @param t1	az elsõ TopListElement, amit össze szeretnénk hasonlítani
 * @param t2	a második TopListElement, amit össze szeretnénk hasonlítani
 * @return	0, ha egyenlõek,
 * 			-1, ha az elsõnél a körök száma több
 * 			+1, egyébként
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
