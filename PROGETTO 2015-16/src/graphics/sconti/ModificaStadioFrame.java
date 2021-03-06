package graphics.sconti;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;

import combo.renderers.StadioComboRenderer;
import struttura.Stadio;

/**
 * Classe che modella un frame per la modifica delle informazioni di uno Stadio
 * 
 * @author Gaetano Antonucci
 * @author Maurizio Casciano
 */
public class ModificaStadioFrame extends JFrame implements Serializable {

	/**
	 * Crea un nuovo frame che permette di aggiungere sconti agli stadi passati
	 * in input.
	 * 
	 * @param stadi
	 *            Lo Stadio da modificare.
	 * @throws IllegalArgumentException
	 *             Se la dimensione degli stadi e' uguale a 0.
	 * @throws NullPointerException
	 *             Se l'ArrayList stadi e' null.
	 * @author Maurizio Casciano
	 * @author Gaetano Antonucci
	 */
	public ModificaStadioFrame(ArrayList<Stadio> stadi) throws IllegalArgumentException, NullPointerException {
		super("Sconto Stadio");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1, 10, 10));

		if (stadi == null) {
			throw new NullPointerException("stadi non pu� essere null");
		}

		if (stadi.size() == 0) {
			throw new IllegalArgumentException("E' richiesta la presenza di almeno uno stadio.");
		}

		this.stadi = stadi;

		this.init();
		this.pack();
		this.setResizable(false);
	}

	/**
	 * Inizializza i vari componenti di questo frame.
	 * 
	 * @author Maurizio Casciano
	 */
	private void init() {
		this.initStadiComboPanel();
		this.initCapienzaPanel();
		this.initPrezzoPanel();
		this.initButtonPanel();
	}

	/**
	 * Inizializza il pannello per selezionare lo stadio.
	 * 
	 * @author Maurizio Casciano
	 * @author Gaetano Antonucci
	 */
	private void initStadiComboPanel() {
		this.stadioLabel = new JLabel("Stadio: ");
		this.stadiCombo = new JComboBox<>(this.stadi.toArray(new Stadio[0]));
		this.stadiCombo.setRenderer(new StadioComboRenderer());
		/*
		 * Sicuro, perche' nel caso non vi siano stadi viene lanciata
		 * l'eccezione IllegalArgumentException.
		 */
		this.stadiCombo.setSelectedIndex(0);

		this.stadiCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(e.getStateChange() == ItemEvent.SELECTED);
					Stadio stadio = (Stadio) stadiCombo.getSelectedItem();

					capienzaSpinner.setValue(stadio.getCapienzaDesiderataStadio());
					prezzoSpinner.setValue(stadio.getPrezzoPerPartita());

				}

			}
		});

		this.stadiComboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.stadiComboPanel.add(this.stadioLabel);
		this.stadiComboPanel.add(this.stadiCombo);
		this.add(this.stadiComboPanel);
	}

	/**
	 * Inizializza il pannello per selezionare la capienza dello stadio.
	 * 
	 * @author Maurizio Casciano
	 */
	private void initCapienzaPanel() {
		this.capienzaLabel = new JLabel("Capienza: ");
		this.capienzaSpinner = new JSpinner(
				new SpinnerNumberModel(((Stadio) stadiCombo.getSelectedItem()).getCapienzaDesiderataStadio(),
						Stadio.CAPIENZA_MINIMA, Stadio.CAPIENZA_MASSIMA, 500));
		JSpinner.NumberEditor editor = (NumberEditor) capienzaSpinner.getEditor();
		editor.getTextField().setEditable(false);
		this.capienzaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.capienzaPanel.add(this.capienzaLabel);
		this.capienzaPanel.add(this.capienzaSpinner);
		this.add(this.capienzaPanel);
	}

	/**
	 * Inizializza il pannello per selezionare il prezzo per partita dello
	 * Stadio
	 * 
	 * @author Maurizio Casciano
	 */
	private void initPrezzoPanel() {
		this.prezzoLabel = new JLabel("Prezzo per Partita: ");
		this.prezzoSpinner = new JSpinner(
				new SpinnerNumberModel(((Stadio) stadiCombo.getSelectedItem()).getPrezzoPerPartita(),
						Stadio.PREZZO_MINIMO, Stadio.PREZZO_MASSIMO, 0.5));
		String currencySymbol = DecimalFormatSymbols.getInstance().getCurrencySymbol();
		String currencyPattern = currencySymbol + " ###" + '.' + "00";

		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(prezzoSpinner, currencyPattern);
		editor.getTextField().setEditable(false);
		this.prezzoSpinner.setEditor(editor);
		this.prezzoPartitaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.prezzoPartitaPanel.add(this.prezzoLabel);
		this.prezzoPartitaPanel.add(this.prezzoSpinner);
		this.add(this.prezzoPartitaPanel);
	}

	/**
	 * Inizializza il pannello contenente i pulsanti per applicare le modifiche
	 * o per annullare l'operazione.
	 * 
	 * @author Gaetano Antonucci
	 * @author Maurizio Casciano
	 */
	private void initButtonPanel() {
		this.applicaModificheButton = new JButton("Applica Modifiche");
		this.applicaModificheButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Stadio stadio = (Stadio) stadiCombo.getSelectedItem();
				int capienza = (int) capienzaSpinner.getValue();
				double prezzo = (double) prezzoSpinner.getValue();

				stadio.setCapienzaStadio(capienza);
				stadio.setPrezzoPerPartita(prezzo);

				dispose();
			}
		});

		this.annullaButton = new JButton("Annulla");
		this.annullaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				dispose();
			}
		});

		this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.buttonPanel.add(this.applicaModificheButton);
		this.buttonPanel.add(this.annullaButton);
		this.add(this.buttonPanel);
	}

	private static final long serialVersionUID = 7236146811425602651L;
	private ArrayList<Stadio> stadi;
	private JLabel stadioLabel;
	private JComboBox<Stadio> stadiCombo;
	private JPanel stadiComboPanel;
	private JLabel capienzaLabel;
	private JSpinner capienzaSpinner;
	private JPanel capienzaPanel;
	private JLabel prezzoLabel;
	private JSpinner prezzoSpinner;
	private JPanel prezzoPartitaPanel;
	private JButton applicaModificheButton, annullaButton;
	private JPanel buttonPanel;

	public static void main(String[] args) {

		Stadio s = new Stadio("Meazza", 100000, 10);
		ArrayList<Stadio> stadi = new ArrayList<>();
		stadi.add(s);
		stadi.add(new Stadio("Olimpico", 60000, 30));
		stadi.add(new Stadio("Camp Nou", 99354, 30));

		ModificaStadioFrame scontoStadioFrame = new ModificaStadioFrame(stadi);
		scontoStadioFrame.setLocationRelativeTo(null);
		scontoStadioFrame.setVisible(true);
	}
}
