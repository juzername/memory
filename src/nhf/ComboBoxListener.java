package nhf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * A ComboBoxListener osztály az új játék indításakor megjelenõ JComboBox-szal kapcsolatos eseményeket kezeli,
 * ezért implementálja az ActionListener osztályt.
 * A JCombobox-szal az állatoknak élõhely szerinti csoportját tudjuk kiválasztani.
 * @author Lenovo
 *
 */
public class ComboBoxListener implements ActionListener{
	@SuppressWarnings("rawtypes")
	private JComboBox combobox;
	private Game game;
	
	@SuppressWarnings("rawtypes")
	public ComboBoxListener(JComboBox cb, Game g) {
		combobox = cb;
		game = g;
	}
	
/**
 * Az ActionListener osztály implemetálása miatt az actionPerformed() függvény felüldefiniálása szükséges.
 * A JCombobox listájából aktuálisan kiválasztott elemet beállítja a leendõ játék állatcsoportjának,
 * azaz a kártyákon szereplõ állatok élõhelyét, ami szerint csoportosítva vannak.
 * A megadott esemény elõre beállított parancsát vizsgálva döntjük el, hogy mi lesz ez.
 * (Farm, Dzsungel, Házi kedvenc, Folyó vagy Szafari)
 * 
 * @param e	az esemény (ActionEvent), aminek a beállított parancsa alapján állítunk élõhelyet. 
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedItem = combobox.getSelectedItem().toString();
		
		if(selectedItem.equals("Farm"))
			game.setHabitat(Habitat.FARM);
		
		if(selectedItem.equals("Dzsungel"))
			game.setHabitat(Habitat.JUNGLE);
		
		if(selectedItem.equals("Házi kedvenc"))
			game.setHabitat(Habitat.PET);
		
		if(selectedItem.equals("Folyó"))
			game.setHabitat(Habitat.RIVER);
		
		if(selectedItem.equals("Szafari"))
			game.setHabitat(Habitat.SAFARI);
	}
}
