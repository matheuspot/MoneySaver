package fonte;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe usada para representar o movo de visualização Histograma no relatório.
 */
public class Histograma implements Modo<List<Double>> {
	private final List<Double> valores = new ArrayList<Double>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Double> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {
		double valorTotal;

		for (int i = 1; i < 13; i++) {
			valorTotal = 0;

			for (Transacao transacao : transacoesFiltrada) {
				if (transacao.getDataDeInsercao().getMonthValue() == i)
					valorTotal += Math.abs(transacao.getValor());
			}
			valores.add(valorTotal);
		}
		return valores;
	}
}
