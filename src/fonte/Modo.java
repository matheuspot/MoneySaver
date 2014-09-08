package fonte;

import java.util.List;

/**
 * Interface para se escolher o modo de visualização da lista que irá para o
 * relatório.
 *
 * @param <T>
 *            O tipo de lista que deseja-se usar no relatório.
 */
public interface Modo<T> {

	/**
	 * Método que irá preparar a lista para visualização.
	 * 
	 * @param transacoesFiltrada
	 *            A lista de transações.
	 * @return Retorna a lista de transações preparada para visualização.
	 */
	public T getTransacoesPreparada(List<Transacao> transacoesFiltrada);
}
