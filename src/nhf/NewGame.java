package nhf;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * A NewGame oszt�ly a j�t�k kezdet�n megjelen� ablak be�ll�t�sai�rt, a j�t�kosn�v eset�ben az esem�ny kezel�s��rt felel�s.
 * Ezeken a megjelen� paneleken el�rhet� a j�t�kos sz�m�ra a j�t�k jellemz�inek a be�ll�t�sa,
 * mint a j�t�kosn�v, j�t�km� �s az �l�hely. K�t gomb is megjelenik,
 * az egyikkel a j�t�kt�bla megjelen�t�s�vel kezdet�t veszi a j�t�k, a m�sikkal kil�phet�nk a programb�l.
 * Az ablak megjelen�t�se miatt a JFrame oszt�ly lesz�rmazottja.
 * @author Sivado
 *
 */
@SuppressWarnings("serial")
public class NewGame extends JFrame{
	private JLabel name_label;
	public JTextField name;
	private JLabel mode_label;
	private ButtonGroup mode;
	private JRadioButton wordCardsButton;
	private JRadioButton memoryButton;
	private JLabel habitat_label;
	@SuppressWarnings("rawtypes")
	private JComboBox habitatcb;
	private JButton ok;
	private JButton kilep;	
	Game game;
	
	public NewGame() {
		game = new Game();
	}

/**
 * A NewGame oszt�ly f� f�ggv�nye, ami az �j j�t�k be�ll�t�sait tartalmaz� ablakot megjelen�ti.
 * <p>
 * Egy JLabel a "J�t�kos" sz�val jelzi  a melette lev� JTextFieldbe v�rt �rt�ket.
 * Ha a felhaszn�l� nem ad meg semmit, az alap�rtelmezett j�t�kosn�v "J�t�kos",
 * egy�b esetben a leend� j�t�k j�t�kosnev�t a beg�pelt Stringgel inicializ�lja.
 * <p>
 * A "J�t�kt�pus" Stringet tartalmaz� JLabel jelzi, hogy a "Mem�riaj�t�k" �s a "Sz�k�rty�k" r�di�gombok k�z�l
 * a kiv�lasztottal iniv�cializ�lja a leend� j�t�k j�t�km�dj�t.
 * Alap�rtelmezetten a Mem�riaj�t�k van kiv�lasztva �s ezzel van inicializ�lva a Game j�t�km�dja.
 * Az ehhez tartoz� esem�nyeket a ButtonListener oszt�ly kezeli.
 * <p>
 * Az "�l�hely" Stringet tartalmaz� JLabel jelzi, hogy a mellette lev� JComboBoxb�l kiv�lasztott elem lesz
 * a j�t�kban szerepl� k�rty�kon l�v� �llatok �l�helye. (Alap�rtelmezett: FARM). Ehhez egy ComboBoxListener objektum az esem�nyfigyel�.
 * <p>
 * A j�t�k c�m�t �s a paneleket is megjelen�ti.
 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initNewGameFrame() {
		game.setPlayer("J�t�kos");
		name_label = new JLabel("J�t�kos neve:");
		
		name = new JTextField("J�t�kos", 20);
		name.setEditable(true);
		name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setPlayer(name.getText());
			}
		});
		
		
		game.setGameMode(GameMode.MEMORYGAME);
		mode_label = new JLabel("J�t�kt�pus:");
		mode = new ButtonGroup();
		
		memoryButton = new JRadioButton("Mem�riaj�t�k");
		memoryButton.setActionCommand("memory");
		memoryButton.addActionListener(new ButtonListener(this));
		memoryButton.setSelected(true);
		
		wordCardsButton = new JRadioButton("Sz�k�rty�k");
		wordCardsButton.setActionCommand("wordcards");
		wordCardsButton.addActionListener(new ButtonListener(this));
		
		mode.add(memoryButton);
		mode.add(wordCardsButton);
		
		
		game.setHabitat(Habitat.FARM);
		habitat_label = new JLabel("�L�hely:");
		String[] habitatsList = {"Farm", "Dzsungel", "H�zi kedvenc", "Foly�", "Szafari"};
		
		habitatcb = new JComboBox(habitatsList);
		habitatcb.setSelectedItem(0);
		habitatcb.addActionListener(new ComboBoxListener(habitatcb, this.game));
		
		
		ok = new JButton("OK");
		ok.addActionListener(new ButtonListener(this));
		ok.setActionCommand("ok");
		
		kilep = new JButton("Kil�p");
		kilep.addActionListener(new ButtonListener(this));
		kilep.setActionCommand("kilep");
		
		
		GridLayout gl = new GridLayout();
		gl.setColumns(1);
		gl.setRows(6);
		setLayout(gl);
		
		
		JPanel panel0 = new JPanel();
		JLabel titleHUN = new JLabel("No� b�rk�ja mem�riaj�t�k");
		titleHUN.setFont(new Font("ALgerian", Font.PLAIN, 36));
		
		JPanel panel00 = new JPanel();
		JLabel titleENG =new JLabel("Noah's Ark Memory Game");
		titleENG.setFont(new Font("ALgerian", Font.PLAIN, 28));
		
		panel0.add(titleHUN);
		panel00.add(titleENG);
		
		
		JPanel panel1 = new JPanel();
		panel1.add(name_label);
		panel1.add(name);
		
		JPanel panel2 = new JPanel();
		panel2.add(mode_label);
		panel2.add(memoryButton);
		panel2.add(wordCardsButton);
				
		JPanel panel3 = new JPanel();
		panel3.add(habitat_label);
		panel3.add(habitatcb);
		
		JPanel panel4 = new JPanel();
		panel4.add(ok);
		panel4.add(kilep);
		
		add(panel0);
		add(panel00);
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);

		pack();
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
