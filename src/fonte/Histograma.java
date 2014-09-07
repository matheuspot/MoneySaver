package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Modo<List<Double>> {
	private List<Transacao> transacoes;
	private List<Double> valores;
	
	public Histograma(List<Transacao> transacoesFiltrada) {
		transacoes = transacoesFiltrada;
	}

	@Override
	public List<Double> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {
		
		for (Transacao transacao : transacoesFiltrada) {
			valores.add(transacao.getValor());
		} return valores;
	}
}
