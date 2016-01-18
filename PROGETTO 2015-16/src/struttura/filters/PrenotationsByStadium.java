package struttura.filters;

import java.io.Serializable;

import struttura.PrenotazioneV2;
import struttura.Stadio;

public class PrenotationsByStadium implements PrenotationFilter, Serializable{
	
	public PrenotationsByStadium(Stadio stadio){
		this.stadio = stadio;
	}
	
	@Override
	public boolean accept(PrenotazioneV2 prenotazione) {
		boolean result = false;
		
		if(prenotazione.getBigliettoPrenotato().getPartita().getStadio().equals(this.stadio)){
			result = true;
		}
		
		return result;
	}
	
	
	private Stadio stadio;

	private static final long serialVersionUID = -3926849092030927287L;

}