package struttura.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import struttura.Partita;
import struttura.Sconto;
import struttura.Squadra;
import struttura.Stadio;
import struttura.TipoSconto;

/**
 * Classe di call-back per l'interfaccia ScontoFilter per gli Sconti in base
 * alla {@link Partita}
 * 
 * @author Gaetano Antonucci
 */
@SuppressWarnings("unused")
public class ScontoByMatchFilter implements ScontoFilter, Serializable {

	/*
	 * public ScontoPerPartita(Sconti sconto){ this.sconto = sconto; }
	 */

	/**
	 * Costruisce un filtro sugli sconti
	 * 
	 * @param sconti
	 *            - l'ArrayList degli Sconti
	 */
	public ScontoByMatchFilter(ArrayList<Sconto> sconti) {
		this.sconti = sconti;
	}

	@Override
	public void updateCurrentSconto(int i) {
		this.sconto = this.sconti.get(i);
	}

	@Override
	public boolean accept(Partita partitaDaVerificare, GregorianCalendar dataDaVerificare) {
		Partita partitaSconto = this.sconto.getPartita();
		boolean result = false;
		
		//System.out.println("partita in accept " + partitaSconto);

		/*
		 * Si effettua una verifica diversa rispetto agli altri sconti in quanto
		 * quello su partita e' valido un solo giorno
		 */
		/*int annoInizioValidita = sconto.getInizioValidita().get(Calendar.YEAR);
		int meseInizioValidita = sconto.getInizioValidita().get(Calendar.MONTH);
		int giornoInizioValidita = sconto.getInizioValidita().get(Calendar.DAY_OF_MONTH);

		int annoFineValidita = sconto.getFineValidita().get(Calendar.YEAR);
		int meseFineValidita = sconto.getFineValidita().get(Calendar.MONTH);
		int giornoFineValidita = sconto.getFineValidita().get(Calendar.DAY_OF_MONTH);

		int annoPartita = partitaDiCalcio.getData().get(Calendar.YEAR);
		int mesePartita = partitaDiCalcio.getData().get(Calendar.MONTH);
		int giornoPartita = partitaDiCalcio.getData().get(Calendar.DAY_OF_MONTH); */
		
		//System.out.println("Data partita " + partitaDaVerificare.getData().getTime());
		//System.out.println("Date sconto" + sconto.getInizioValidita().getTime() + " --- " + sconto.getFineValidita().getTime());
		
		if (partitaSconto != null) {
			/*if ((partitaDiCalcio.equals(sconto.getPartita())) && (annoPartita == annoInizioValidita)
					&& (mesePartita == meseInizioValidita) && (giornoPartita == giornoInizioValidita)
					&& (annoPartita == annoFineValidita) && (mesePartita == meseFineValidita)
					&& (giornoPartita == giornoFineValidita)) { */
			if(partitaDaVerificare.equals(sconto.getPartita()) 
				&& !(dataDaVerificare.before(sconto.getInizioValidita()))
				&& !(dataDaVerificare.after(sconto.getFineValidita())))	{ 
				result = true;
			}
		}

		return result;
	}

	private Sconto sconto;
	private ArrayList<Sconto> sconti;

	private static final long serialVersionUID = 5163413202426097316L;

}
