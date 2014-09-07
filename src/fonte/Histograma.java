package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Modo<List<Double>> {
	private List<Double> valores;

	@Override
	public List<Double> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {
		double valorTotal;
		
		for (int i = 1; i < 13; i++) {
			
			valores = new ArrayList<>();
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
