package fonte;

import java.util.List;

public class Lista implements Modo<List<Transacao>> {
	private List<Transacao> transacoes;

	public Lista(List<Transacao> transacoesFiltrada) {
		transacoes = transacoesFiltrada;
	}

	@Override
	public List<Transacao> getTransacoesPreparada(
			List<Transacao> transacoesFiltrada) {
		return transacoes;
	}
}
