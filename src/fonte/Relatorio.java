package fonte;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Relatorio {
	
	private Usuario userLogado;
	private List<Transacao> transacoes = new ArrayList<>();
	private Modo modoDeVizualizar;
	
	public Relatorio(Usuario userLogado) throws Exception {
		if (userLogado.getContaAtiva().getTransacoesExistentes() == null)
			throw new Exception("Cadastre uma transacao primeiro!");
		
		for (Conta conta : userLogado.getContas()) {
			for (Transacao transacao : conta.getTransacoesExistentes()) {
				transacoes.add(transacao);
			}
		}
		
		this.userLogado = userLogado;
		modoDeVizualizar = null;
	}
	
	public void filtraPorMes(int mes) {
		for (Transacao transacao : transacoes) {
			if (transacao.getDataDeInsercao().getMonthValue() != mes)
				transacoes.remove(transacao);
		}
	}
	
	public void filtraPorCategoria(Categoria categoria) {
		for (Transacao transacao : transacoes) {
			if (!transacao.getCategoria().equals(categoria))
				transacoes.remove(transacao);
		}
	}
	
	public void filtraPorConta(Conta conta) {
		for (Conta contaDoUser : userLogado.getContas()) {
			if (contaDoUser.equals(conta))
				transacoes = contaDoUser.getTransacoesExistentes();
		}
	}
	
	public void filtraPorData(LocalDate dataIni, LocalDate dataFin) {
		for (Transacao transacao : transacoes) {
			if (transacao.getDataDeInsercao().isBefore(dataIni) || transacao.getDataDeInsercao().isAfter(dataFin))
				transacoes.remove(transacao);
		}
	}
	
	public void filtraPorTipo(String tipoDaTransacao) {
		for (Transacao transacao : transacoes) {
			if (tipoDaTransacao == "despesa")
				if (transacao.getValor() > 0)
					transacoes.remove(transacao);
			if (tipoDaTransacao == "provento")
				if (transacao.getValor() < 0)
					transacoes.remove(transacao);
		}
	}
	
	public List<?> getTransacoesPreparadas(Modo modo) {
		return (List<?>) modo.getTransacoesPreparada(transacoes);
	}
}
