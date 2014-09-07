package fonte;

import java.util.List;

public class Histograma implements Modo<List<Double>> {
	private List<Double> valores;

	@Override
	public List<Double> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {

		for (Transacao transacao : transacoesFiltrada) {
			valores.add(Math.abs(transacao.getValor()));
		}
		return valores;
	}
}
