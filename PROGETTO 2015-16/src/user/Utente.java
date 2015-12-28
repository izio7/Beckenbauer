package user;

import java.io.Serializable;
import password.Password;
import password.WeakPasswordException;
import password.PasswordHash;

/**
 * La classe Utente rappresenta l'Utente di un sistema informatico. Ogni Utente
 * � dotato di un nome, un cognome, una username ed una passwordHash.
 */
public abstract class Utente implements Serializable, Cloneable{

	/**
	 * Crea un nuovo Utente di un sistema informatico.
	 * 
	 * @param nome
	 *            Il nome dell'Utente.
	 * @param cognome
	 *            Il cognome dell'Utente.
	 * @param username
	 *            L'username dell'Utente, utilizzato per accedere ad un sistema
	 *            informatico.
	 * @param password
	 *            La password dell'Utente, utilizzata per accedere ad un sistema
	 *            informatico.
	 * @throws WeakPasswordException
	 *             Indica che la password scelta non rispecchia i requisiti
	 *             minimi di sicurezza.
	 */
	public Utente(String nome, String cognome, String username, String password) throws WeakPasswordException {

		if (!Password.check(password)) {
			throw new WeakPasswordException();
		}

		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.passwordHash = PasswordHash.createHash(password);
	}

	/**
	 * Restituisce il nome dell'Utente.
	 * 
	 * @return Il nome dell'Utente.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce il cognome dell'Utente.
	 * 
	 * @return Il cognome dell'Utente.
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Restituisce la username dell'Utente.
	 * 
	 * @return La username dell'Utente.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Confronta l'Hash della password passata in input con la passwordHash di
	 * questo Utente.
	 * 
	 * @param password
	 *            La password di cui bisogna controllare la corrispondenza con
	 *            la password reale di questo Utente.
	 * @return true se la password � corretta, false altrimenti.
	 */
	public boolean matchPassword(String password) {
		return PasswordHash.validatePassword(password, this.passwordHash);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [nome = " + nome + ", cognome = " + cognome + ", username = "
				+ username + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equalsIgnoreCase(other.username))
			return false;
		return true;
	}

	private static final long serialVersionUID = -5901640760099125643L;
	private String nome, cognome, username, passwordHash;
}
