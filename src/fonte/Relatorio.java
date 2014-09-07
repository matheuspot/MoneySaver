package fonte;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {

	private List<Transacao> transacoes = new ArrayList<>();

	public Relatorio(Conta contaAtiva) throws Exception {
		transacoes = contaAtiva.getTransacoesExistentes();
		if (transacoes == null || transacoes.isEmpty())
			throw new Exception("Conta sem transações.");
	}

	public void filtraPorCategoria(Categoria categoria) {
		for (Transacao transacao : transacoes) {
			if (!categoria.equals(transacao.getCategoria()))
				transacoes.remove(transacao);
		}
	}

	public void filtraPorData(int mesInicial, int mesFinal) {
		for (Transacao transacao : transacoes) {
			if (transacao.getDataDeInsercao().getMonthValue() < mesInicial
					|| transacao.getDataDeInsercao().getMonthValue() > mesFinal)
				transacoes.remove(transacao);
		}
	}

	public void filtraPorTipo(String tipoDaTransacao) {
		for (Transacao transacao : transacoes) {
			if (tipoDaTransacao == "despesa")
				if (transacao.getValor() > 0)
					transacoes.remove(transacao);
			else if (tipoDaTransacao == "provento")
				if (transacao.getValor() < 0)
					transacoes.remove(transacao);
		}
	}
	
	public List<Transacao> getListaFiltrada() {
		return transacoes;	
	}
	
	public List<?> getTransacoesPreparadas(Modo<?> modo) {
		return (List<?>) modo.getTransacoesPreparada(transacoes);
	}
}
