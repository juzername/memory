package nhf;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Az ActionListenerTopList oszt�ly arra �r�dott, hogy az eredm�nylista men�pontot v�lasztva az esem�nyt kezelje.
 * Az ActionListener interf�szt implement�ja.
 * @author Sivado
 *
 */
public class ActionListenerTopList implements ActionListener{
	Game game;
	
	public ActionListenerTopList(Game g) {
		game = g;
	}
/**
 * Az ActionListener interf�szt implemet�lva sz�ks�ges ennek a f�ggv�nynek a fel�l�r�sa.
 * L�trehoz egy �j JFramet, amin a TopListElementeket tartalmaz� list�t jelen�ti meg JLabelt haszn�lva.
 * Ha a j�t�k v�get �rt, az �j eredm�nnyel friss�ti a t�bl�t,
 * ellenkez� esetben egy �j TopListElementtel l�trehoz egy TopListSerializationt, �s �gy m�r be tudja olvasni annak elemit, hogy megjelen�tse azokat. 
 */
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		
		frame.setSize(300, game.tls.toplist.size()*60);
		frame.setTitle("Eredm�nylista");
		
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
