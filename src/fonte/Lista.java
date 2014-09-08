package fonte;

import java.util.List;

/**
 * Classe usada para representar o modo de visualização Lista no relatório.
 */
public class Lista implements Modo<List<Transacao>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Transacao> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {
		return transacoesFiltrada;
	}
}
