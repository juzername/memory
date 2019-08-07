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
 * A NewGame osztály a játék kezdetén megjelenõ ablak beállításaiért, a játékosnév esetében az esemény kezeléséért felelõs.
 * Ezeken a megjelenõ paneleken elérhetõ a játékos számára a játék jellemzõinek a beállítása,
 * mint a játékosnév, játékmó és az élõhely. Két gomb is megjelenik,
 * az egyikkel a játéktábla megjelenítésével kezdetét veszi a játék, a másikkal kiléphetünk a programból.
 * Az ablak megjelenítése miatt a JFrame osztály leszármazottja.
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
 * A NewGame osztály fõ függvénye, ami az új játék beállításait tartalmazó ablakot megjeleníti.
 * <p>
 * Egy JLabel a "Játékos" szóval jelzi  a melette levõ JTextFieldbe várt értéket.
 * Ha a felhasználó nem ad meg semmit, az alapértelmezett játékosnév "Játékos",
 * egyéb esetben a leendõ játék játékosnevét a begépelt Stringgel inicializálja.
 * <p>
 * A "Játéktípus" Stringet tartalmazó JLabel jelzi, hogy a "Memóriajáték" és a "Szókártyák" rádiógombok közül
 * a kiválasztottal inivócializálja a leendõ játék játékmódját.
 * Alapértelmezetten a Memóriajáték van kiválasztva és ezzel van inicializálva a Game játékmódja.
 * Az ehhez tartozó eseményeket a ButtonListener osztály kezeli.
 * <p>
 * Az "Élõhely" Stringet tartalmazó JLabel jelzi, hogy a mellette levõ JComboBoxból kiválasztott elem lesz
 * a játékban szereplõ kártyákon lévõ állatok élõhelye. (Alapértelmezett: FARM). Ehhez egy ComboBoxListener objektum az eseményfigyelõ.
 * <p>
 * A játék címét és a paneleket is megjeleníti.
 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initNewGameFrame() {
		game.setPlayer("Játékos");
		name_label = new JLabel("Játékos neve:");
		
		name = new JTextField("Játékos", 20);
		name.setEditable(true);
		name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setPlayer(name.getText());
			}
		});
		
		
		game.setGameMode(GameMode.MEMORYGAME);
		mode_label = new JLabel("Játéktípus:");
		mode = new ButtonGroup();
		
		memoryButton = new JRadioButton("Memóriajáték");
		memoryButton.setActionCommand("memory");
		memoryButton.addActionListener(new ButtonListener(this));
		memoryButton.setSelected(true);
		
		wordCardsButton = new JRadioButton("Szókártyák");
		wordCardsButton.setActionCommand("wordcards");
		wordCardsButton.addActionListener(new ButtonListener(this));
		
		mode.add(memoryButton);
		mode.add(wordCardsButton);
		
		
		game.setHabitat(Habitat.FARM);
		habitat_label = new JLabel("ÉLõhely:");
		String[] habitatsList = {"Farm", "Dzsungel", "Házi kedvenc", "Folyó", "Szafari"};
		
		habitatcb = new JComboBox(habitatsList);
		habitatcb.setSelectedItem(0);
		habitatcb.addActionListener(new ComboBoxListener(habitatcb, this.game));
		
		
		ok = new JButton("OK");
		ok.addActionListener(new ButtonListener(this));
		ok.setActionCommand("ok");
		
		kilep = new JButton("Kilép");
		kilep.addActionListener(new ButtonListener(this));
		kilep.setActionCommand("kilep");
		
		
		GridLayout gl = new GridLayout();
		gl.setColumns(1);
		gl.setRows(6);
		setLayout(gl);
		
		
		JPanel panel0 = new JPanel();
		JLabel titleHUN = new JLabel("Noé bárkája memóriajáték");
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
