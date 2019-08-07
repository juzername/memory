package nhf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** ButtonListener osztály az új játék kezdõképernyõjén megjelenõ gombokhoz rendelt események létrehozására.
 * Az ActionListener interfészt implementálja, hogy az eseményekhez (adott gombra kattintás) utasításokat rendeljen.
 * @author Sivado
 *
 */
public class ButtonListener implements ActionListener{
	private NewGame ng;
	
	public ButtonListener(NewGame n) {
		ng = n;
	}
	
/**
 * 	Az ActionListener osztályt implemetálva szükséges az actionperformed() függvény felülírása.
 *  A függvény paramétereként megadott ActionEvent parancsszavát használjuk ahhoz, hogy az eseményt kiváltó elemet azonosítsuk.
 *  <p>
 *  Ha az "OK" gombra kattint a felhasználó, akkor egy új játékot indít a bevitt vagy alapértelmezett beállításokkal.
 *  Ha a "Kilép" gombra kattint, akkor az ablak bezáródik.
 *  A "Memóriajáték" és "Szójáték" rádiógombok közül a kiválasztott határozza meg a leendõ játék típusát.
 *  A Felhasználónév begépelésével játékos nevét adhatjuk meg.
 *  
 * @param e: Az az esemény (ActionEvent), aminek a hatását beállítjuk.
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")) {
			ng.game.createTable();
			ng.dispose();
		}
		
		if(e.getActionCommand().equals("kilep")) {
			ng.dispose();
		}
		
		if(e.getActionCommand().equals("wordcards")) {
			ng.game.setGameMode(GameMode.WORDCARDSGAME);
		}
		
		if(e.getActionCommand().equals("memory")) {
			ng.game.setGameMode(GameMode.MEMORYGAME);
		}
	}		
}
