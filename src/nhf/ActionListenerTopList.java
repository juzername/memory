package nhf;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Az ActionListenerTopList osztály arra íródott, hogy az eredménylista menüpontot választva az eseményt kezelje.
 * Az ActionListener interfészt implementája.
 * @author Sivado
 *
 */
public class ActionListenerTopList implements ActionListener{
	Game game;
	
	public ActionListenerTopList(Game g) {
		game = g;
	}
/**
 * Az ActionListener interfészt implemetálva szükséges ennek a függvénynek a felülírása.
 * Létrehoz egy új JFramet, amin a TopListElementeket tartalmazó listát jeleníti meg JLabelt használva.
 * Ha a játék véget ért, az új eredménnyel frissíti a táblát,
 * ellenkezõ esetben egy új TopListElementtel létrehoz egy TopListSerializationt, és így már be tudja olvasni annak elemit, hogy megjelenítse azokat. 
 */
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		
		frame.setSize(300, game.tls.toplist.size()*60);
		frame.setTitle("Eredménylista");
		
		if(game.hasWon()) {
			JPanel panel = new JPanel(new GridLayout(game.tls.toplist.size(), 1));
		
			try {
				game.tls.readFromFile("toplist.txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(int i = 0; i < game.tls.toplist.size(); i++) {
				JLabel label = new JLabel(game.tls.toplist.get(i).toString());
				panel.add(label);
				}
			
			frame.add(panel);
			}
		
		else {
			game.tls = new TopListSerialization(new TopListElement(game.getRound(), 0, game.getPlayer()));
			JPanel panel = new JPanel(new GridLayout(game.tls.toplist.size(), 1));
			
			try {
				game.tls.readFromFile("toplist.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			for(int i = 0; i < game.tls.toplist.size(); i++) {
				JLabel label = new JLabel(game.tls.toplist.get(i).toString());
				panel.add(label);
				}
			
			frame.add(panel);
		}
		
		frame.setVisible(true);	
	}
}
