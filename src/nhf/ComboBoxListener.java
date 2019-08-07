package nhf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * A ComboBoxListener oszt�ly az �j j�t�k ind�t�sakor megjelen� JComboBox-szal kapcsolatos esem�nyeket kezeli,
 * ez�rt implement�lja az ActionListener oszt�lyt.
 * A JCombobox-szal az �llatoknak �l�hely szerinti csoportj�t tudjuk kiv�lasztani.
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
 * Az ActionListener oszt�ly implemet�l�sa miatt az actionPerformed() f�ggv�ny fel�ldefini�l�sa sz�ks�ges.
 * A JCombobox list�j�b�l aktu�lisan kiv�lasztott elemet be�ll�tja a leend� j�t�k �llatcsoportj�nak,
 * azaz a k�rty�kon szerepl� �llatok �l�hely�t, ami szerint csoportos�tva vannak.
 * A megadott esem�ny el�re be�ll�tott parancs�t vizsg�lva d�ntj�k el, hogy mi lesz ez.
 * (Farm, Dzsungel, H�zi kedvenc, Foly� vagy Szafari)
 * 
 * @param e	az esem�ny (ActionEvent), aminek a be�ll�tott parancsa alapj�n �ll�tunk �l�helyet. 
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedItem = combobox.getSelectedItem().toString();
		
		if(selectedItem.equals("Farm"))
			game.setHabitat(Habitat.FARM);
		
		if(selectedItem.equals("Dzsungel"))
			game.setHabitat(Habitat.JUNGLE);
		
		if(selectedItem.equals("H�zi kedvenc"))
			game.setHabitat(Habitat.PET);
		
		if(selectedItem.equals("Foly�"))
			game.setHabitat(Habitat.RIVER);
		
		if(selectedItem.equals("Szafari"))
			game.setHabitat(Habitat.SAFARI);
	}
}
