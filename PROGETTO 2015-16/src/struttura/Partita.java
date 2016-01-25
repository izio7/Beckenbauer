package struttura;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Classe che modella una partita di calcio
 * 
 * @author Maurizio Casciano
 * @author Gaetano Antonucci
 */
public class Partita implements Serializable {

	/**
	 * Costruisce un oggetto Partita in base ai parametri impostati
	 * 
	 * @param squadraInCasa - la {@link Squadra} che gioca in Casa
	 * @param squadraInTrasferta - la {@link Squadra} che gioca in Trasferta
	 * @param stadio - lo {@link Stadio} in cui sara' giocata la partita
	 * @param data - la data in cui sara' disputata la partita
	 * 
	 * @author Maurizio Casciano
	 */
	public Partita(Squadra squadraInCasa, Squadra squadraInTrasferta, Stadio stadio, GregorianCalendar data) {
		this.squadraInCasa = squadraInCasa;
		this.squadraInTrasferta = squadraInTrasferta;
		this.stadio = stadio;
		this.data = data;

		/********************************************/
		/* ArrayList per salvare lo stato dei posti */
		/********************************************/
		this.settori = this.stadio.getSettoriClone();
	}

	/**
	 * Crea una nuova partita con valori di default, usata per inserire una
	 * nuova riga nella JTable delle partite, e poi permettere la modifica
	 * all'utente.
	 * 
	 * @author Maurizio Casciano
	 */
	public Partita() {
		this.squadraInCasa = new Squadra("-");
		this.squadraInTrasferta = new Squadra("-");
		this.stadio = new Stadio("-", Stadio.CAPIENZA_MINIMA, Stadio.PREZZO_MINIMO);
		this.data = new GregorianCalendar();
		this.settori = this.stadio.getSettoriClone();
	}

	/**
	 * Restituisce i settori dello stadio per questa partita.
	 *
	 * @return ArrayList<Settore> contente i settori dello stadio per questa partita
	 * @author Gaetano Antonucci
	 */
	public ArrayList<Settore> getSettori() {
		return this.settori;
	}

	/**
	 * Ripristina lo stato del posto della prenotazione passata in input a
	 * {@link SeatStatus#LIBERO}.
	 * 
	 * @param prenotazioneEliminata
	 *            La prenotazione eliminata o scaduta.
	 * @author Maurizio Casciano
	 */
	public void resetSeatStatus(Prenotazione prenotazioneEliminata) {
		Posto posto = prenotazioneEliminata.getPosto();
		Settore settore = posto.getSettore();

		for (Settore s : this.settori) {
			if (s.equals(settore)) {
				for (Posto p : s.getPosti()) {
					if (p.equals(posto)) {
						p.setStato(SeatStatus.LIBERO);
						break;
					}
				}
				break;
			}
		}
	}

	/**
	 * Restituisce la squadra che gioca in casa.
	 * 
	 * @return La squadra che gioca in casa.
	 * @author Maurizio Casciano
	 */
	public Squadra getSquadraInCasa() {
		return this.squadraInCasa;
	}

	/**
	 * Imposta la squadra che gioca in casa.
	 * 
	 * @param squadraInCasa
	 *            La squadra che gioca in casa.
	 * @author Maurizio Casciano
	 */
	public void setSquadraInCasa(Squadra squadraInCasa) {
		this.squadraInCasa = squadraInCasa;
	}

	/**
	 * Restituisce la squadra che gioca in trasferta.
	 * 
	 * @return La squadra che gioca intrasferta.
	 * @author Maurizio Casciano
	 */
	public Squadra getSquadraInTrasferta() {
		return this.squadraInTrasferta;
	}

	/**
	 * Imposta la squadra che gioca in trasferta.
	 * 
	 * @param squadraInCasa
	 *            La squadra che gioca in trasferta.
	 * @author Maurizio Casciano
	 */
	public void setSquadraInTrasferta(Squadra squadraInTrasferta) {
		this.squadraInTrasferta = squadraInTrasferta;
	}

	/**
	 * Restituisce lo stadio in cui sara' disputata la partita.
	 * 
	 * @return Lo stadio in cui sara' disputata la partita.
	 * @author Maurizio Casciano
	 */
	public Stadio getStadio() {
		return this.stadio;
	}

	/**
	 * Imposta lo stadio in cui sara' disputata la partita.
	 * 
	 * @warning ATTENZIONE: le prenotazioni e gli acquisti presenti andranno persi.
	 * 
	 * @param stadio - Lo stadio in cui sara' disputata la partita.
	 * @author Maurizio Casciano
	 */
	public void setStadio(Stadio stadio) {
		if (stadio != null) {
			this.stadio = stadio;
			this.settori = this.stadio.getSettoriClone();
		}
	}

	/**
	 * Restituisce la data in cui sara' disputata la partita.
	 * 
	 * @return La data in cui sara' disputata la partita.
	 * @author Maurizio Casciano
	 */
	public GregorianCalendar getData() {
		return this.data;
	}

	/**
	 * Imposta la data in cui sara' disputata la partita.
	 * 
	 * @param data - La data in cui sara' disputata la partita.
	 * @author Maurizio Casciano
	 */
	public void setData(GregorianCalendar data) {
		this.data = data;
	}

	/**
	 * Restituisce le informazioni della partita
	 * @author Maurizio Casciano
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + squadraInCasa.getNome() + " - " + squadraInTrasferta.getNome()
				+ " Stadio: " + stadio.getNome() + " " + DATE_FORMAT.format(data.getTime());
	}

	/**
	 * Restituisce i posti dello stadio per la partita corrente
	 * 
	 * @return ArrayList<Posto> con i posti dello stadio
	 */
	public ArrayList<Posto> getPosti() {
		return posti;
	}

	/**
	 * Verifica se l'oggetto corrente è uguale all'oggetto passato come parametro
	 * 
	 * @param obj - l'oggetto su cui effettuare la verifica
	 * @return {@code true} se quest'oggetto è uguale all'oggetto passato come parametro, {@code false} altrimenti
	 * @author Gaetano Antonucci
	 */
	@Override
	public boolean equals(Object obj) { // (GA)
		boolean result = false;

		if (this == obj) {
			result = true;
		}

		if (obj == null) {
			result = false;
		}

		if (getClass() != obj.getClass())
			result = false;

		Partita other = (Partita) obj;

		if ((this.data.equals(other.data)) && (this.squadraInCasa.equals(other.squadraInCasa))
				&& (this.squadraInTrasferta.equals(other.squadraInTrasferta))) {
			result = true;
		}

		return result;
	}

	private Squadra squadraInCasa, squadraInTrasferta;
	private Stadio stadio;
	private GregorianCalendar data;

	/*
	 * Arraylist per tenere traccia dei posti prenotati, acquistati.
	 */
	private ArrayList<Posto> posti;
	private ArrayList<Settore> settori;

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E  dd/MM/yyyy  HH:mm");

	private static final long serialVersionUID = 1548727127680681004L;

}
