package fonte;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {

	private ArrayList<Transacao> transacoes;
	private ArrayList<Transacao> transacoesAuxiliar;

	public Relatorio(Conta contaAtiva) throws Exception {
		transacoes = (ArrayList<Transacao>) contaAtiva.getTransacoesExistentes();
		transacoesAuxiliar = (ArrayList<Transacao>) transacoes.clone();
		
		if (transacoes == null || transacoes.isEmpty())
			throw new Exception("Conta sem transações.");
	}

	public void filtraPorCategoria(Categoria categoria) {
		for (Transacao transacao : transacoes) {
			if (!categoria.equals(transacao.getCategoria()))
				transacoesAuxiliar.remove(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}

	public void filtraPorData(int mesInicial, int mesFinal) {
		
		for (Transacao transacao : transacoes) {
			if (transacao.getDataDeInsercao().getMonthValue() < mesInicial
					&& transacao.getDataDeInsercao().getMonthValue() > mesFinal)
				transacoesAuxiliar.remove(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}

	public void filtraPorTipo(String tipoDaTransacao) {
		
		for (Transacao transacao : transacoes) {
<<<<<<< HEAD
			if (tipoDaTransacao.equals("despesa"))
				if (transacao.getValor() > 0)
					transacoesAuxiliar.remove(transacao);
			else if (tipoDaTransacao.equals("provento"))
=======
			if (tipoDaTransacao == "Despesa")
				if (transacao.getValor() > 0)
					transacoesAuxiliar.remove(transacao);
			else if (tipoDaTransacao == "Provento")
>>>>>>> origin/master
				if (transacao.getValor() < 0)
					transacoesAuxiliar.add(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}
	
	public List<Transacao> getListaFiltrada() {
		return transacoes;	
	}
	
	public List<?> getTransacoesPreparadas(Modo<?> modo) {
		return (List<?>) modo.getTransacoesPreparada(transacoes);
	}
}
