package excecao;

/**
 * Exceção do programa MoneySaver.
 */
public class MoneySaverException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da exceção.
	 * 
	 * @param mensagem
	 *            A mensagem da exceção.
	 */
	public MoneySaverException(String mensagem) {
		super(mensagem);
	}
}
