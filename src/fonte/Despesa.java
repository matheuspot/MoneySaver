package fonte;

import java.time.LocalDate;
import excecao.MoneySaverException;

/**
 * Classe usada para representar uma despesa.
 */
public class Despesa extends Transacao {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe Despesa.
	 * 
	 * @param descricao
	 *            A descrição da despesa.
	 * @param dataDeInsercao
	 *            A data de inserção da despesa.
	 * @param valor
	 *            O valor da despesa.
	 * @param categoria
	 *            A categoria da despesa.
	 * @param recorrencia
	 *            A recorrência da despesa.
	 * @throws MoneySaverException
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public Despesa(String descricao, LocalDate dataDeInsercao, double valor,
			Categoria categoria, String recorrencia) throws MoneySaverException {
		super(descricao, dataDeInsercao, valor, categoria, recorrencia);
	}

	/**
	 * Método de acesso ao valor da despesa.
	 */
	@Override
	public double getValor() {
		return -super.getValor();
	}
}
