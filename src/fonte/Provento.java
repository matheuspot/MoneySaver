package fonte;

import java.time.LocalDate;

/**
 * Classe usada para representar um provento.
 */
public class Provento extends Transacao {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe Provento.
	 * 
	 * @param descricao
	 *            A descrição do provento.
	 * @param dataDeInsercao
	 *            A data de inserção do provento.
	 * @param valor
	 *            O valor do provento.
	 * @param categoria
	 *            A categoria do provento.
	 * @param recorrencia
	 *            A recorrência do provento.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public Provento(String descricao, LocalDate dataDeInsercao, double valor,
			Categoria categoria, String recorrencia) throws Exception {
		super(descricao, dataDeInsercao, valor, categoria, recorrencia);
	}

	/**
	 * Método de acesso ao valor do provento.
	 */
	@Override
	public double getValor() {
		return super.getValor();
	}
}
