package nhf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** ButtonListener oszt�ly az �j j�t�k kezd�k�perny�j�n megjelen� gombokhoz rendelt esem�nyek l�trehoz�s�ra.
 * Az ActionListener interf�szt implement�lja, hogy az esem�nyekhez (adott gombra kattint�s) utas�t�sokat rendeljen.
 * @author Sivado
 *
 */
public class ButtonListener implements ActionListener{
	private NewGame ng;
	
	public ButtonListener(NewGame n) {
		ng = n;
	}
	
/**
 * 	Az ActionListener oszt�lyt implemet�lva sz�ks�ges az actionperformed() f�ggv�ny fel�l�r�sa.
 *  A f�ggv�ny param�terek�nt megadott ActionEvent parancsszav�t haszn�ljuk ahhoz, hogy az esem�nyt kiv�lt� elemet azonos�tsuk.
 *  <p>
 *  Ha az "OK" gombra kattint a felhaszn�l�, akkor egy �j j�t�kot ind�t a bevitt vagy alap�rtelmezett be�ll�t�sokkal.
 *  Ha a "Kil�p" gombra kattint, akkor az ablak bez�r�dik.
 *  A "Mem�riaj�t�k" �s "Sz�j�t�k" r�di�gombok k�z�l a kiv�lasztott hat�rozza meg a leend� j�t�k t�pus�t.
 *  A Felhaszn�l�n�v beg�pel�s�vel j�t�kos nev�t adhatjuk meg.
 *  
 * @param e: Az az esem�ny (ActionEvent), aminek a hat�s�t be�ll�tjuk.
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
